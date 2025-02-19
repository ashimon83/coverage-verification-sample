import { vi } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import { BookList } from './BookList';
import { useBooks } from '../hooks/useBooks';

// useBooks hookをモック
vi.mock('../hooks/useBooks');

describe('BookList', () => {
  beforeEach(() => {
    // デフォルトのモック実装
    vi.mocked(useBooks).mockReturnValue({
      books: [],
      loading: true,
      error: null,
      fetchBooks: vi.fn(),
      createBook: vi.fn(),
      updateBook: vi.fn(),
      deleteBook: vi.fn(),
    });
  });

  it('初期状態でローディングが表示される', () => {
    render(<BookList />);
    expect(screen.getByText('読み込み中...')).toBeInTheDocument();
  });

  it('エラーが発生した場合にエラーメッセージが表示される', () => {
    vi.mocked(useBooks).mockReturnValue({
      books: [],
      loading: false,
      error: 'エラーが発生しました',
      fetchBooks: vi.fn(),
      createBook: vi.fn(),
      updateBook: vi.fn(),
      deleteBook: vi.fn(),
    });

    render(<BookList />);
    expect(screen.getByText('エラーが発生しました')).toBeInTheDocument();
  });

  it('書籍一覧が表示される', async () => {
    const mockBooks = [
      {
        id: 1,
        title: 'テスト駆動開発',
        author: 'Kent Beck',
        isbn: '978-4-274-21788-7',
        price: 3300,
        createdAt: '2024-02-18T00:00:00.000Z',
      },
    ];

    vi.mocked(useBooks).mockReturnValue({
      books: mockBooks,
      loading: false,
      error: null,
      fetchBooks: vi.fn(),
      createBook: vi.fn(),
      updateBook: vi.fn(),
      deleteBook: vi.fn(),
    });

    render(<BookList />);
    
    await waitFor(() => {
      expect(screen.getByText('書籍一覧')).toBeInTheDocument();
      expect(screen.getByText('テスト駆動開発')).toBeInTheDocument();
      expect(screen.getByText('著者: Kent Beck')).toBeInTheDocument();
    });
  });
}); 