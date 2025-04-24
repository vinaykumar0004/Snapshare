// auth-service/server.js
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const axios = require('axios');
const nodemailer = require('nodemailer');
const path = require('path');


const app = express();
app.use(bodyParser.json());

mongoose.connect('mongodb://localhost:27017/authdb', { useNewUrlParser: true, useUnifiedTopology: true });

const userSchema = new mongoose.Schema({
    name: String,
    email: { type: String, required: true, unique: true },
    password: String,
    isVerified: { type: Boolean, default: false },
    isPhotographer: { type: Boolean, default: false },
    specificCode: String // This will store the specific code
});

const User = mongoose.model('User', userSchema);

// // Identify User Endpoint
// app.post('/identifyUser', async (req, res) => {
//     const { email } = req.body;
//     const user = await User.findOne({ email });
//     if (user) {
//         res.json({ isPhotographer: user.isPhotographer });
//     } else {
//         res.status(404).json({ message: 'User not found' });
//     }
// });
 
app.post('/register', async (req, res) => {
    const { name, email, password, specificCode } = req.body;

    const user = await User.findOne({ email });

    if (!user || !user.isVerified) {
            return res.status(400).send('Please verify your email before registering');
     }

    try {
        // Check if the specific code is already registered
        const specificCodeExists = await User.findOne({ specificCode });

        if (specificCodeExists) {
            return res.status(400).json({ success: false, message: 'This specific code is already in use. Please enter a unique code.' });
        }

        // Hash the password asynchronously
        const hashedPassword = await bcrypt.hash(password, 10);

        // Determine if the user is a photographer based on the specific code
        const isPhotographer = specificCode.startsWith('P');

        // Create a new user instance
        // const newUser = new User({
        //     name,
        //     email,
        //     password: hashedPassword,
        //     isPhotographer,
        //     specificCode,
        //     isVerified: true  // Assuming user is already verified here
        // });
        // Create a new user instance
    // user = new User({ name, email, password: hashedPassword, isPhotographer, specificCode });
 
    user.name = name;
    user.password = hashedPassword;
    user.specificCode = specificCode;
    user.isPhotographer = isPhotographer;
    user.isVerified = true;

        // Save the new user to the database
        // await newUser.save();

        // res.status(200).json({ success: true, message: 'Registration successful' });
        // Save the user to the database
        await user.save();
    
        // res.send('User registered successfully.');
        res.status(200).json({ success: true, message: 'Registration successful' });

    } catch (err) {
        console.error('Error during registration:', err); // Log the full error for debugging
        res.status(500).json({ success: false, message: 'Registration failed', error: err.message });
    }
});




app.post('/login', async (req, res) => {
    const { email, password, specificCode } = req.body;
   
    try {
    const user = await User.findOne({ email });
    const isPhotographer = specificCode.startsWith('P');

    if (user && await bcrypt.compare(password, user.password)) {
        if (!user.isVerified) {
            return res.status(403).send('Email not verified.');
        }
        if (user.specificCode !== specificCode) {
            return res.status(401).send('Invalid specific code.');
        }
        if (user.isPhotographer !== isPhotographer) {
            return res.status(401).send('Invalid specific code.');
        }
        const token = jwt.sign({ userId: user._id }, 'aDatAqeuUuOvqYgvU0m0eueNc5gSdT6Wk1f846Ld7KQ=');
        res.status(200).send({ token, message: 'Login successful' });
    } 
} catch (err) {
    console.error('Invalid credentials', err);
    res.status(400).send('Invalid credentials');
}
});

// app.use(express.static('public'));  // Adjust path to your 'public' directory

// Serve static files from the 'public' folder
// app.use('/images', express.static(path.join(__dirname, 'auth-service/verified.gif')));

app.get('/verify-email', async (req, res) => {
    const { token } = req.query;
    try {
        const decoded = jwt.verify(token, 'aDatAqeuUuOvqYgvU0m0eueNc5gSdT6Wk1f846Ld7KQ=');
        const email = decoded.email;
        console.log('Decoded email:', email);

        // Find the user by email and update the isVerified field
        const user = await User.findOneAndUpdate(
            { email },
            { isVerified: true },
            { new: true }
        );        

        // let user = await User.findOne({ email });
        if (!user) {
            console.log('User not found, creating a new user');
            const newUser = new User({ email, isVerified: true });
            await newUser.save();
            console.log('New user created and verified:', newUser);
        
        } else {
            console.log('User allready verified', user);
         
        }
         
        res.sendFile(path.join(__dirname,  'public/images/verified.gif'));
        // res.sendFile(path.join(__dirname,  'public/images/verified.gif'));

        
    } catch (err) {
        console.error('Error verifying token:', err);
        res.status(400).send('Invalid token.');
    }
});
 
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'abhishekdeswal74@gmail.com',
        pass: 'zzge cupn fpjx goqq'
    }
});
app.post('/send-verification-email', async (req, res) => {
    const { email } = req.body;

    if (!email) {
        return res.status(400).json({ status: 'error', message: 'Email is required' });
    }

    try {
        // Check if the email exists in the database
        const user = await User.findOne({ email });

        if (!user) {
            // If the email does not exist, send the verification email
            const token = jwt.sign({ email }, 'aDatAqeuUuOvqYgvU0m0eueNc5gSdT6Wk1f846Ld7KQ=', { expiresIn: '1h' });
            const verificationLink = `http://192.168.1.7:4000/verify-email?token=${token}`;

            const mailOptions = {
                from: 'abhishekdeswal74@gmail.com',
                to: email,
                subject: 'Email Verification',
                text: `Please verify your email by clicking on the following link: ${verificationLink}`
            };

            await transporter.sendMail(mailOptions);
            return res.json({ status: 'success', message: 'Verification email sent.' });
        }

        // If email exists, check if it's already verified
        if (user.isVerified) {
            return res.status(400).json({ status: 'error', message: 'Email is already exist and also verified.' });
        }

        // If the email exists but is not verified, resend the verification email
        const token = jwt.sign({ email }, 'aDatAqeuUuOvqYgvU0m0eueNc5gSdT6Wk1f846Ld7KQ=', { expiresIn: '1h' });
        const verificationLink = `http://192.168.1.7:4000/verify-email?token=${token}`;

        const mailOptions = {
            from: 'abhishekdeswal74@gmail.com',
            to: email,
            subject: 'Email Verification',
            text: `Please verify your email by clicking on the following link: ${verificationLink}`
        };

        await transporter.sendMail(mailOptions);
        return res.json({ status: 'success', message: 'Verification email sent again. Please check your email.' });
        
    } catch (err) {
        console.error('Error sending email:', err);
        return res.status(500).json({ status: 'error', message: 'Failed to send verification email.' });
    }
});


app.get('/health', (req, res) => {
    res.status(200).send('Auth Service is running');
});


app.listen(4000, () => {
    console.log('Auth service running on port 4000');
});
