import { useState, useCallback } from 'react';

export interface Book {
  id: number;
  title: string;
  author: string;
  isbn: string;
  price: number;
  createdAt: string;
}

export interface CreateBookRequest {
  title: string;
  author: string;
  isbn: string;
  price: number;
}

export interface UpdateBookRequest {
  title?: string;
  author?: string;
  price?: number;
}

export const useBooks = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchBooks = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch('/api/books');
      if (!response.ok) {
        throw new Error('書籍の取得に失敗しました');
      }
      const data = await response.json();
      setBooks(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : '予期せぬエラーが発生しました');
    } finally {
      setLoading(false);
    }
  }, []);

  const createBook = useCallback(async (book: CreateBookRequest) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch('/api/books', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(book),
      });
      if (!response.ok) {
        throw new Error('書籍の作成に失敗しました');
      }
      await fetchBooks();
    } catch (err) {
      setError(err instanceof Error ? err.message : '予期せぬエラーが発生しました');
    } finally {
      setLoading(false);
    }
  }, [fetchBooks]);

  const updateBook = useCallback(async (id: number, book: UpdateBookRequest) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`/api/books/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(book),
      });
      if (!response.ok) {
        throw new Error('書籍の更新に失敗しました');
      }
      await fetchBooks();
    } catch (err) {
      setError(err instanceof Error ? err.message : '予期せぬエラーが発生しました');
    } finally {
      setLoading(false);
    }
  }, [fetchBooks]);

  const deleteBook = useCallback(async (id: number) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`/api/books/${id}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        throw new Error('書籍の削除に失敗しました');
      }
      await fetchBooks();
    } catch (err) {
      setError(err instanceof Error ? err.message : '予期せぬエラーが発生しました');
    } finally {
      setLoading(false);
    }
  }, [fetchBooks]);

  return {
    books,
    loading,
    error,
    fetchBooks,
    createBook,
    updateBook,
    deleteBook,
  };
}; 