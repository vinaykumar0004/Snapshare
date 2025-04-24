// // email-service/server.js
// const express = require('express');
// const bodyParser = require('body-parser');
// const nodemailer = require('nodemailer');
// const jwt = require('jsonwebtoken');

// const app = express();
// app.use(bodyParser.json());

// const transporter = nodemailer.createTransport({
//     service: 'gmail',
//     auth: {
//         user: 'abhishekdeswal74@gmail.com',
//         pass: 'zzge cupn fpjx goqq'
//     }
// });

// app.post('/send-verification-email', async (req, res) => {
//     const { email, token } = req.body;
//     if (!email) {
//         return res.status(400).json({ status: 'error', message: 'Email is required' });
//     }
//     try {
//         const token = jwt.sign({ email }, 'aDatAqeuUuOvqYgvU0m0eueNc5gSdT6Wk1f846Ld7KQ=', { expiresIn: '1h' });
//         const verificationLink = http://192.168.119.203:4000/verify-email?token=${token};

//     transporter.sendMail({
//         from: 'abhishekdeswal74@gmail.com',
//         to: email,
//         subject: 'Email Verification',
//         text: Please verify your email by clicking on the following link: ${verificationLink}
//     });

//         await transporter.sendMail(mailOptions);
//         res.json({ status: 'success', message: 'Verification email sent.' });
//     } catch (err) {
//         res.status(500).json({ status: 'error', message: 'Failed to send verification email.' });
//     }
// });


// app.get('/health', (req, res) => {
//     res.status(200).send('Email Service is running');
// });

// app.listen(4003, () => {
//     console.log('Email service running on port 4003');
// });

const express = require('express');
const bodyParser = require('body-parser');
const nodemailer = require('nodemailer');
const jwt = require('jsonwebtoken');


const app = express();
app.use(bodyParser.json());



 

const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'abhishekdeswal74@gmail.com',
        pass: 'zzge cupn fpjx goqq'
    }
});

app.post('/send-verification-email', async (req, res) => {
    const { email } = req.body;
    // const { email, token } = req.body;
    

    if (!email) {
    // if (!email || !token) {
        return res.status(400).json({ status: 'error', message: 'Email is required' });
    }

    try {
        
        // Check if the email exists in the database
        const user = await User.findOne({ email });
        
        if (!user) {
            // If the email does not exist, send the verification email
        const token = jwt.sign({ email }, 'aDatAqeuUuOvqYgvU0m0eueNc5gSdT6Wk1f846Ld7KQ=', { expiresIn: '1h' });
        const verificationLink = `http://192.168.146.203:4000/verify-email?token=${token}`;

        const mailOptions = {
            from: 'abhishekdeswal74@gmail.com',
            to: email,
            subject: 'Email Verification',
            text: `Please verify your email by clicking on the following link: ${verificationLink}`
        };

        await transporter.sendMail(mailOptions);
        res.json({ status: 'success', message: 'Verification email sent.' });
    } 
    // If email exists, check if it's already verified
    if (user.isVerified) {
        return res.status(400).json({ status: 'error', message: 'Email is already verified.' });
    }
     // If the email exists but is not verified, resend the verification email
     const token = jwt.sign({ email }, 'aDatAqeuUuOvqYgvU0m0eueNc5gSdT6Wk1f846Ld7KQ=', { expiresIn: '1h' });
     const verificationLink = `http://192.168.146.203:4000/verify-email?token=${token}`;

     const mailOptions = {
         from: 'abhishekdeswal74@gmail.com',
         to: email,
         subject: 'Email Verification',
         text: `Please verify your email by clicking on the following link: ${verificationLink}`
     };

     await transporter.sendMail(mailOptions);
     return res.json({ status: 'success', message: 'Verification email sent again. Please check your email.' });

 } 
 
    catch (err) {
        console.error('Error sending email:', err);
        res.status(500).json({ status: 'error', message: 'Failed to send verification email.' });
    }
});

app.get('/health', (req, res) => {
    res.status(200).send('Email Service is running');
});

app.listen(4003, () => {
    console.log('Email service running on port 4003');
});
