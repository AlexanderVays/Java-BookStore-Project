package a01038582.books2.data;

/**
 * @author Alexander Vays, A01038582
 *
 */
public class Purchase {

	private int purchaseId;
	private int customerId;
	private int bookId;
	private double price;

	public static class Builder {
		private int purchaseId;
		private int customerId;
		private int bookId;
		private double price;

		/**
		 * @param purchaseId
		 *            the purchase Id
		 * @param customerId
		 *            the customer Id
		 * @param bookId
		 *            the book Id
		 */
		public Builder(int purchaseId, int customerId, int bookId) {
			this.purchaseId = purchaseId;
			this.customerId = customerId;
			this.bookId = bookId;
		}

		/**
		 * @param price
		 *            the price to set
		 * @return Purchase Builder
		 */
		public Builder setPrice(double price) {
			this.price = price;
			return this;
		}

		/**
		 * @return Purchase object
		 */
		public Purchase build() {
			return new Purchase(this);
		}
	}

	private Purchase(Builder builder) {
		purchaseId = builder.purchaseId;
		customerId = builder.customerId;
		bookId = builder.bookId;
		price = builder.price;
	}

	/**
	 * @return purchaseId the purchase Id
	 */
	public int getPurchaseId() {
		return purchaseId;
	}

	/**
	 * @param purchaseId
	 *            the purchaseId to set
	 */
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * @return customerId the customer Id
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return bookId the book Id
	 */
	public int getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return price the book price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the book price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[purchaseId=" + purchaseId + ", customerId=" + customerId + ", bookId="
				+ bookId + ", price=" + price + "]";
	}
}
