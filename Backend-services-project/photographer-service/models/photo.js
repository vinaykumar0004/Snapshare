const mongoose = require('mongoose');

const photoSchema = new mongoose.Schema({
    path: { type: String, required: true },
    specificCode: { type: String, required: true },
});

const Photo = mongoose.model('Photo', photoSchema);

module.exports = Photo;
