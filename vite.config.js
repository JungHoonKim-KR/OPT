import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueDevTools from "vite-plugin-vue-devtools";

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(), vueDevTools()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  server: {
    proxy: {
      "/question": {
        target:
          "http://ec2-43-201-179-196.ap-northeast-2.compute.amazonaws.com:8080/", // 실제 백엔드 주소
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/question/, '')  // 필요시 경로 재작성
      },
    },
  },
});
