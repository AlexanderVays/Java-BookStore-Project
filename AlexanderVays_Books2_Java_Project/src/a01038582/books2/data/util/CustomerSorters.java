package a01038582.books2.data.util;

import java.util.Comparator;

import a01038582.books2.data.Customer;

/**
 * @author Alexander Vays, A01038582
 * @version 1.0
 */
public class CustomerSorters {

	public static class CompareByJoinedDate implements Comparator<Customer> {
		@Override
		public int compare(Customer customer1, Customer customer2) {
			return customer1.getJoinedDate().compareTo(customer2.getJoinedDate());
		}
	}

	public static class DescendingOptionSet implements Comparator<Customer> {
		@Override
		public int compare(Customer customer1, Customer customer2) {
			return customer2.getJoinedDate().compareTo(customer1.getJoinedDate());
		}
	}

	public static class CompareByLastName implements Comparator<Customer> {
		@Override
		public int compare(Customer customer1, Customer customer2) {
			return customer1.getLastName().compareTo(customer2.getLastName());
		}
	}

	public static class CompareByLastNameDescending implements Comparator<Customer> {
		@Override
		public int compare(Customer customer1, Customer customer2) {
			return customer2.getLastName().compareTo(customer1.getLastName());
		}
	}
}
