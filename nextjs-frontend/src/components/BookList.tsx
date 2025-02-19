import { useEffect } from 'react';
import { useBooks, Book } from '../hooks/useBooks';

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('ja-JP', {
    style: 'currency',
    currency: 'JPY'
  }).format(price);
};

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('ja-JP', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });
};

export const BookList = () => {
  const { books, loading, error, fetchBooks, deleteBook } = useBooks();

  useEffect(() => {
    fetchBooks();
  }, [fetchBooks]);

  if (loading) {
    return <div>読み込み中...</div>;
  }

  if (error) {
    return <div className="text-red-600">{error}</div>;
  }

  return (
    <div className="container mx-auto px-4">
      <h1 className="text-2xl font-bold mb-4">書籍一覧</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {books.map((book) => (
          <BookCard
            key={book.id}
            book={book}
            onDelete={() => deleteBook(book.id)}
          />
        ))}
      </div>
    </div>
  );
};

interface BookCardProps {
  book: Book;
  onDelete: () => void;
}

const BookCard = ({ book, onDelete }: BookCardProps) => {
  return (
    <div className="bg-white rounded-lg shadow-md p-4">
      <h2 className="text-xl font-semibold mb-2">{book.title}</h2>
      <p className="text-gray-600 mb-2" data-testid="author">
        著者: {book.author}
      </p>
      <p className="text-gray-600 mb-2">ISBN: {book.isbn}</p>
      <p className="text-gray-600 mb-2">価格: {formatPrice(book.price)}</p>
      <p className="text-gray-600 mb-4">登録日: {formatDate(book.createdAt)}</p>
      <div className="flex justify-end space-x-2">
        <button
          onClick={onDelete}
          className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition-colors"
        >
          削除
        </button>
      </div>
    </div>
  );
}; 