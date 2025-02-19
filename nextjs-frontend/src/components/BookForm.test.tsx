import React from 'react';
import { render, screen } from '@testing-library/react';
import { BookForm } from './BookForm';

describe('BookForm', () => {
  it('必要な入力フィールドが表示される', () => {
    render(<BookForm />);
    
    expect(screen.getByLabelText('タイトル')).toBeInTheDocument();
    expect(screen.getByLabelText('著者')).toBeInTheDocument();
    expect(screen.getByLabelText('ISBN')).toBeInTheDocument();
    expect(screen.getByLabelText('価格')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: '追加' })).toBeInTheDocument();
  });
}); 