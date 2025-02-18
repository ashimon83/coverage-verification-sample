'use client';

import { BookList } from '../components/BookList';
import { BookForm } from '../components/BookForm';

export default function Home() {
  return (
    <main className="min-h-screen bg-gray-100">
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-8">書籍管理システム</h1>
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <div>
            <BookForm />
          </div>
          <div>
            <BookList />
          </div>
        </div>
      </div>
    </main>
  );
}
