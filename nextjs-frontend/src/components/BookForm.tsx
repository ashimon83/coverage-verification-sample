import { useState } from 'react';
import { useBooks, CreateBookRequest } from '../hooks/useBooks';

export const BookForm = () => {
  const { createBook, error } = useBooks();
  const [formData, setFormData] = useState<CreateBookRequest>({
    title: '',
    author: '',
    isbn: '',
    price: 0,
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await createBook(formData);
    setFormData({
      title: '',
      author: '',
      isbn: '',
      price: 0,
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'price' ? Number(value) : value,
    }));
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <h2 className="text-xl font-bold mb-4">書籍の追加</h2>
      {error && <div className="text-red-600 mb-4">{error}</div>}
      <form onSubmit={handleSubmit} className="max-w-lg">
        <div className="mb-4">
          <label htmlFor="title" className="block text-gray-700 mb-2">
            タイトル
          </label>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />
        </div>

        <div className="mb-4">
          <label htmlFor="author" className="block text-gray-700 mb-2">
            著者
          </label>
          <input
            type="text"
            id="author"
            name="author"
            value={formData.author}
            onChange={handleChange}
            required
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />
        </div>

        <div className="mb-4">
          <label htmlFor="isbn" className="block text-gray-700 mb-2">
            ISBN
          </label>
          <input
            type="text"
            id="isbn"
            name="isbn"
            value={formData.isbn}
            onChange={handleChange}
            required
            pattern="\\d{3}-\\d-\\d{4}-\\d{4}-\\d"
            title="ISBN-13形式で入力してください（例: 978-4-1234-5678-9）"
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />
        </div>

        <div className="mb-4">
          <label htmlFor="price" className="block text-gray-700 mb-2">
            価格
          </label>
          <input
            type="number"
            id="price"
            name="price"
            value={formData.price}
            onChange={handleChange}
            required
            min="0"
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
          />
        </div>

        <button
          type="submit"
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition-colors"
        >
          追加
        </button>
      </form>
    </div>
  );
}; 