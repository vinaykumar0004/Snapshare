const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const multer = require('multer');
const fs = require('fs');
const path = require('path');
const cors = require('cors');

const app = express();
app.use(bodyParser.json());
app.use(cors());

// Set up multer for file storage
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        const uploadPath = 'P:/photoss/';
        fs.mkdirSync(uploadPath, { recursive: true });
        cb(null, uploadPath);
    },
    filename: function (req, file, cb) {
        cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname));
    }
});

 
const upload = multer({ storage: storage });

// Serve static files from the uploads directory
app.use('/photoss', express.static('uploads'));
// app.use('/photo-receiver-Uploaded', express.static('photo-receiver-Uploaded'));

 
 mongoose.connect('mongodb://localhost:27017/photodb', { useNewUrlParser: true, useUnifiedTopology: true });

// MongoDB Schema and Model
const photoSchema = new mongoose.Schema({
    path: String,
    specificCode: String,
    embeddings: [[Number]] // Array of arrays of numbers for storing face embeddings
});

const Photo = mongoose.model('Photo', photoSchema);

app.use(express.json());

// Endpoint for uploading photos from gallery
app.post('/upload-photos', upload.array('photos'), (req, res) => {

    console.log('Received request');
    console.log('specificCode:', req.body.specificCode);
    console.log('Files:', req.files);
    console.log('Embeddings:', req.body.embeddings);

try {
    const specificCode = req.body.specificCode;
    const files = req.files;
    // let embeddings = req.body.embeddings;
    let embeddings = JSON.parse(req.body.embeddings);  // This will correctly parse it into an array of arrays


    const photoDocuments = files.map(file => ({
        path: file.path,
        specificCode: specificCode,
        embeddings: embeddings 
    }));

    Photo.insertMany(photoDocuments)
        .then(() => res.status(200).json({ message: 'Photos uploaded successfully' }))
        .catch(err => {
            console.error('Error inserting into database:', err.message);
            res.status(500).json({ error: 'Database insertion failed' });
        });
} catch (err) {
    console.error('Error processing request:', err.message);
    res.status(500).json({ error: 'Server error' });
}
});

// Health check endpoint
app.get('/health', (req, res) => {
    res.status(200).send('Photo Service is running');
});

// Start the server
const PORT = process.env.PORT || 4002;
app.listen(PORT, () => {
    console.log(`Photo service running on port ${PORT}`);
});


// // Endpoint for uploading photos from Google Drive
// app.post('/upload-google-drive-photos', (req, res) => {
//     const specificCode = req.body.specificCode;
//     const { photos} = req.body; // Assuming photos are URLs

//     const photoDocuments = photos.map(photoUrl => ({
//         path: photoUrl, // Here you should download the file and save it to the server
//         specificCode: specificCode
//     }));

//     Photo.insertMany(photoDocuments)
//         .then(() => res.status(200).json({ message: 'Google Drive photos uploaded successfully' }))
//         .catch(err => res.status(500).json({ error: err.message }));
// });