package a01038582.books2.data;

/**
 * @author Alexander Vays, A01038582
 * @version 1.0
 */
public class Book {

	private long bookId;
	private String isbn;
	private String author;
	private int originalPublicationYear;
	private String originalTitle;
	private double averageRating;
	private int ratingsCount;
	private String imageUrl;

	public static class Builder {
		// Required parameters
		private final long bookId;
		private final String isbn;

		// Optional parameters
		private String author;
		private int originalPublicationYear;
		private String originalTitle;
		private double averageRating;
		private int ratingsCount;
		private String imageUrl;

		public Builder(long bookId, String isbn) {
			this.bookId = bookId;
			this.isbn = isbn;
		}

		/**
		 * @param author
		 *            the author to set
		 * @return the Book Builder
		 */
		public Builder setAuthor(String author) {
			this.author = author;
			return this;
		}

		/**
		 * @param originalPublicationYear
		 *            the originalPublicationYear to set
		 * @return the Book Builder
		 */
		public Builder setOriginalPublicationYear(int originalPublicationYear) {
			this.originalPublicationYear = originalPublicationYear;
			return this;
		}

		/**
		 * @param originalTitle
		 *            the originalTitle to set
		 * @return the Book Builder
		 */
		public Builder setOriginalTitle(String originalTitle) {
			this.originalTitle = originalTitle;
			return this;
		}

		/**
		 * @param averageRating
		 *            the averageRating to set
		 * @return the Book Builder
		 */
		public Builder setAverageRating(double averageRating) {
			this.averageRating = averageRating;
			return this;
		}

		/**
		 * @param ratingsCount
		 *            the ratingsCount to set
		 * @return the Book Builder
		 */
		public Builder setRatingsCount(int ratingsCount) {
			this.ratingsCount = ratingsCount;
			return this;
		}

		/**
		 * @param imageUrl
		 *            the imageUrl to set
		 * @return the Book Builder
		 */
		public Builder setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		/**
		 * @return Book object
		 */
		public Book build() {
			return new Book(this);
		}
	}

	/**
	 * Default Constructor
	 */
	private Book(Builder builder) {
		bookId = builder.bookId;
		isbn = builder.isbn;
		author = builder.author;
		originalTitle = builder.originalTitle;
		originalPublicationYear = builder.originalPublicationYear;
		averageRating = builder.averageRating;
		ratingsCount = builder.ratingsCount;
		imageUrl = builder.imageUrl;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[bookId=" + bookId + ", isbn=" + isbn + ", author=" + author
				+ ", originalPublicationYear=" + originalPublicationYear + ", originalTitle=" + originalTitle
				+ ", averageRating=" + averageRating + ", ratingsCount=" + ratingsCount + ", imageUrl=" + imageUrl
				+ "]";
	}

	/**
	 * @return bookId the book ID
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            the BookId to set
	 */
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return isbn the ISBN
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return author the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return originalPublicationYear the original publication year
	 */
	public int getOriginalPublicationYear() {
		return originalPublicationYear;
	}

	/**
	 * @param originalPublicationYear
	 *            the originalPublicationYear to set
	 */
	public void setOriginalPublicationYear(int originalPublicationYear) {
		this.originalPublicationYear = originalPublicationYear;
	}

	/**
	 * @return originalTitle the original title
	 */
	public String getOriginalTitle() {
		return originalTitle;
	}

	/**
	 * @param originalTitle
	 *            the originalTitle to set
	 */
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	/**
	 * @return averageRating the average rating
	 */
	public double getAverageRating() {
		return averageRating;
	}

	/**
	 * @param averageRating
	 *            the averageRating to set
	 */
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	/**
	 * @return ratingsCount the rating count
	 */
	public int getRatingsCount() {
		return ratingsCount;
	}

	/**
	 * @param ratingsCount
	 *            the ratingsCount to set
	 */
	public void setRatingsCount(int ratingsCount) {
		this.ratingsCount = ratingsCount;
	}

	/**
	 * @return imageUrl the image URL
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Book() {

	}

}