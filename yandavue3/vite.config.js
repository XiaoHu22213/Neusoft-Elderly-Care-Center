import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
	AutoImport({
	      resolvers: [ElementPlusResolver()],
	}),
    Components({
      resolvers: [ElementPlusResolver()],
    })
  ],
  server:{
	  port: 80,
	  host: '0.0.0.0'
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
	extensions:['.js','.vue']
  }
})
