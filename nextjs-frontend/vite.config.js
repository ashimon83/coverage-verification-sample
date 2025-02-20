// vite.config.js
import { defineConfig } from "vite";
import { codecovVitePlugin } from "@codecov/vite-plugin";

export default defineConfig({
  plugins: [
    // Put the Codecov vite plugin after all other plugins
    codecovVitePlugin({
      enableBundleAnalysis: process.env.CODECOV_TOKEN !== undefined,
      bundleName: "coverage-verification-sample-frontend",
      uploadToken: process.env.CODECOV_TOKEN || "", // CI環境でのみトークンを使用
    }),
  ],
});