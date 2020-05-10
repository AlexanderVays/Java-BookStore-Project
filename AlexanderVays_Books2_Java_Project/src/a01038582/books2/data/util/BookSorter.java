package a01038582.books2.data.util;

import java.util.Comparator;

import a01038582.books2.data.Book;

/**
 * @author Alexander Vays, A01038582
 *
 */
public class BookSorter {

	public static class compareByAuthor implements Comparator<Book> {
		@Override
		public int compare(Book book1, Book book2) {
			return book1.getAuthor().compareTo(book2.getAuthor());
		}
	}

	public static class compareByAuthorDescending implements Comparator<Book> {
		@Override
		public int compare(Book book1, Book book2) {
			return book2.getAuthor().compareTo(book1.getAuthor());
		}
	}

	public static class compareByTitle implements Comparator<Book> {
		@Override
		public int compare(Book book1, Book book2) {
			return book1.getOriginalTitle().compareTo(book2.getOriginalTitle());
		}
	}

	public static class compareByTitleDescending implements Comparator<Book> {
		@Override
		public int compare(Book book1, Book book2) {
			return book2.getOriginalTitle().compareTo(book1.getOriginalTitle());
		}
	}
}
