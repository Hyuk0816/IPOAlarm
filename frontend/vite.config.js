import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url';
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: "../../src/main/resources/static"
  },
  server:{
    proxy: {
      '/api' : {
        target: 'http://localhost:8080/',
        changeOrigin: true,
      }
    }
  }
});