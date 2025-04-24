// api-gateway/server.js
const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');

const app = express();

app.use('/auth', createProxyMiddleware({ target: 'http://localhost:4000', changeOrigin: true }));
app.use('/photographer', createProxyMiddleware({ target: 'http://localhost:4001', changeOrigin: true }));
app.use('/photo', createProxyMiddleware({ target: 'http://localhost:4002', changeOrigin: true }));
// app.use('/email', createProxyMiddleware({ target: 'http://localhost:4003', changeOrigin: true }));
// app.use('/upload', createProxyMiddleware({ target: 'http://localhost:5000', changeOrigin: true }));
// app.use('/face', createProxyMiddleware({ target: 'http://localhost:3000', changeOrigin: true }));

// // app.get('/health', (req, res) => {
// //     res.status(200).send('Api-gateway Service is running');
// // });

// // app.listen(5000, () => {
// //     console.log('API Gateway running on port 5000');
// // });

// const express = require('express');
// const httpProxy = require('http-proxy');
// const app = express();
// const apiProxy = httpProxy.createProxyServer();

// app.all('/auth/*', (req, res) => {
//     apiProxy.web(req, res, { target: 'http://localhost:4000' });
// });

// app.all('/photographer/*', (req, res) => {
//     apiProxy.web(req, res, { target: 'http://localhost:4001' });
// });

// app.all('/photo/*', (req, res) => {
//     apiProxy.web(req, res, { target: 'http://localhost:4002' });
// });

// app.all('/email/*', (req, res) => {
//     apiProxy.web(req, res, { target: 'http://localhost:4003' });
// });

// app.all('/face-recognition/*', (req, res) => {
//     apiProxy.web(req, res, { target: 'http://localhost:4004' });
// });

app.get('/', (req, res) => {
    res.send('API Gateway is running\\');
  });

// Health check endpoint
app.get('/health', (req, res) => {
    res.send('API Gateway is running');
});

app.listen(5000, () => {
    console.log('API Gateway running on port 5000');
});
