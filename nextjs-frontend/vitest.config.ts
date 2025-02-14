import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';

console.log('Loading vitest.config.ts');

export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './setupTests.ts',
  },
});

console.log('vitest.config.ts loaded successfully');

