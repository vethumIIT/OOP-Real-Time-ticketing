import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import fs from 'fs';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host:true,
    port:5173,
    https: {
      key: fs.readFileSync('ssl/key.pem'),   // Path to your key.pem
      cert: fs.readFileSync('ssl/cert.pem'), // Path to your cert.pem
    },
    /*proxy: {
      '/api': {
        target: 'http://localhost:8080', // Backend server
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
  }*/
}})
