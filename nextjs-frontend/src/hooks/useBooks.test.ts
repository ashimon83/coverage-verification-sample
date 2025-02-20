import { renderHook, act } from '@testing-library/react';
import { vi, Mock } from 'vitest';
import { useBooks } from './useBooks';

describe('useBooks', () => {
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

  beforeEach(() => {
    vi.resetAllMocks();
    global.fetch = vi.fn();
  });

  describe('fetchBooks', () => {
    it('書籍一覧を取得できる', async () => {
      (global.fetch as Mock).mockResolvedValueOnce({
        ok: true,
        json: async () => mockBooks,
      });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.fetchBooks();
      });

      expect(global.fetch).toHaveBeenCalledWith('/api/books');
      expect(result.current.books).toEqual(mockBooks);
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toBeNull();
    });

    it('APIエラー時にエラーメッセージが設定される', async () => {
      (global.fetch as Mock).mockResolvedValueOnce({
        ok: false,
      });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.fetchBooks();
      });

      expect(result.current.error).toBe('書籍の取得に失敗しました');
      expect(result.current.loading).toBe(false);
    });

    it('ネットワークエラー時にエラーメッセージが設定される', async () => {
      (global.fetch as Mock).mockRejectedValueOnce(new Error('Network error'));

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.fetchBooks();
      });

      expect(result.current.error).toBe('Network error');
      expect(result.current.loading).toBe(false);
    });
  });

  describe('createBook', () => {
    const newBook = {
      title: 'リファクタリング',
      author: 'Martin Fowler',
      isbn: '978-4-274-21788-8',
      price: 4400,
    };

    it('書籍を作成できる', async () => {
      (global.fetch as Mock)
        .mockResolvedValueOnce({
          ok: true,
        })
        .mockResolvedValueOnce({
          ok: true,
          json: async () => [...mockBooks, { ...newBook, id: 2 }],
        });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.createBook(newBook);
      });

      expect(global.fetch).toHaveBeenCalledWith('/api/books', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newBook),
      });
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toBeNull();
    });

    it('APIエラー時にエラーメッセージが設定される', async () => {
      (global.fetch as Mock).mockResolvedValueOnce({
        ok: false,
      });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.createBook(newBook);
      });

      expect(result.current.error).toBe('書籍の作成に失敗しました');
      expect(result.current.loading).toBe(false);
    });
  });

  describe('updateBook', () => {
    const updateData = {
      title: '新しいタイトル',
      price: 5500,
    };

    it('書籍を更新できる', async () => {
      (global.fetch as Mock)
        .mockResolvedValueOnce({
          ok: true,
        })
        .mockResolvedValueOnce({
          ok: true,
          json: async () => [{ ...mockBooks[0], ...updateData }],
        });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.updateBook(1, updateData);
      });

      expect(global.fetch).toHaveBeenCalledWith('/api/books/1', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updateData),
      });
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toBeNull();
    });

    it('APIエラー時にエラーメッセージが設定される', async () => {
      (global.fetch as Mock).mockResolvedValueOnce({
        ok: false,
      });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.updateBook(1, updateData);
      });

      expect(result.current.error).toBe('書籍の更新に失敗しました');
      expect(result.current.loading).toBe(false);
    });
  });

  describe('deleteBook', () => {
    it('書籍を削除できる', async () => {
      (global.fetch as Mock)
        .mockResolvedValueOnce({
          ok: true,
        })
        .mockResolvedValueOnce({
          ok: true,
          json: async () => [],
        });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.deleteBook(1);
      });

      expect(global.fetch).toHaveBeenCalledWith('/api/books/1', {
        method: 'DELETE',
      });
      expect(result.current.loading).toBe(false);
      expect(result.current.error).toBeNull();
    });

    it('APIエラー時にエラーメッセージが設定される', async () => {
      (global.fetch as Mock).mockResolvedValueOnce({
        ok: false,
      });

      const { result } = renderHook(() => useBooks());

      await act(async () => {
        await result.current.deleteBook(1);
      });

      expect(result.current.error).toBe('書籍の削除に失敗しました');
      expect(result.current.loading).toBe(false);
    });
  });
}); 