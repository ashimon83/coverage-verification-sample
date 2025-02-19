import React from 'react';
import { render, screen } from '@testing-library/react';
import Home from './page';

describe('Home', () => {
  it('メインヘッダーが表示される', () => {
    render(<Home />);
    const heading = screen.getByRole('heading', {
      name: /書籍管理システム/i,
    });
    expect(heading).toBeInTheDocument();
  });

  it('書籍追加フォームが表示される', () => {
    render(<Home />);
    const formHeading = screen.getByRole('heading', {
      name: /書籍の追加/i,
    });
    expect(formHeading).toBeInTheDocument();
  });
});