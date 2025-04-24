const express = require('express');
const mongoose = require('mongoose');
const path = require('path');

const app = express();

// Connect to MongoDB
mongoose.connect('mongodb://localhost:27017/photodb', { useNewUrlParser: true, useUnifiedTopology: true });

// Define the photo schema and model
const photoSchema = new mongoose.Schema({
    path: String,
    specificCode: String,
    embeddings: [[Number]]  // Array of arrays for face embeddings
});

const Photo = mongoose.model('Photo', photoSchema);

 // Serve static files from the 'P:\\photoss' directory

app.use('/photoss', express.static(path.join('P:\\photoss')));
 

app.get('/get-photos', async (req, res) => {
    try {
        const { specificCode } = req.query;
        if (!specificCode) {
            return res.status(400).send('Missing specificCode or Invalid');
        }
    
         // Fetch photos with matching specificCode
         const photos = await Photo.find({ specificCode });


        if (photos.length === 0) {
            return res.status(404).send('No photos found with the given specificCode');
        }

         // Format the response to include the URLs and embeddings
         const baseUrl = 'http://192.168.1.7:4001';
         const photoData = photos.map(photo => {
             const photoPath = photo.path.replace('P:\\photoss\\', '/photoss/').replace(/\\/g, '/');
             return {
                 url: `${baseUrl}${photoPath}`,
                 embeddings: photo.embeddings
             };
         });
 

        // res.send(photos);
        res.json(photos);
        } catch (err) {
        console.error('Error fetching photos:', err);
        res.status(500).send('Internal Server Error');
    }
    
});

// Start the server
app.listen(4001, () => {
    console.log('Server is running on port 4001');
});
 



    