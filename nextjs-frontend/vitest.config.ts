import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';

console.log('Loading vitest.config.ts');

export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './setupTests.ts',
    coverage: {
      provider: 'v8',
      reporter: ['text', 'json', 'html', 'lcov', 'clover'],
      reportsDirectory: './coverage',
      exclude: [
        'node_modules/',
        'dist/',
        '**/*.d.ts',
        '**/*.test.{ts,tsx}',
        '**/*.config.{js,ts}',
      ],
    },
  },
});

console.log('vitest.config.ts loaded successfully');

