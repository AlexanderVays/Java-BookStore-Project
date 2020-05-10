package a01038582.books2.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.ApplicationException;
import a01038582.books2.BookOptions;
import a01038582.books2.data.Customer;
import a01038582.books2.data.Purchase;
import a01038582.books2.data.util.CustomerSorters;
import a01038582.books2.data.util.Validator;

/**
 * @author Alexander Vays, A01038582
 * @version 1.0
 */
public class CustomerReader {

	public static final String RECORD_DELIMITER = ":";
	public static final String FIELD_DELIMITER = "\\|";

	private static ArrayList<Customer> customers = new ArrayList<>();

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * private constructor to prevent instantiation
	 */
	private CustomerReader() {
	}

	/**
	 * @return an Array of customers
	 * @throws ApplicationException
	 *             in case not enough number of elements in customer
	 */
	public static ArrayList<Customer> readInputCustomerFile() throws ApplicationException {
		ArrayList<String> rawCustomersData = new ArrayList<>();
		File file = new File("customers.dat");
		BufferedReader br = null;
		LOG.debug("Reading " + file.getAbsolutePath());
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				rawCustomersData.add(line);
			}
			br.close();

		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
				throw new ApplicationException(e.getMessage());
			}

		}
		if (rawCustomersData != null) {
			for (int i = 0; i < rawCustomersData.size(); i++) {
				String[] elements = rawCustomersData.get(i).split(FIELD_DELIMITER);
				if (elements.length < 9) {
					throw new ApplicationException(
							String.format("Expected 9 but got %d: %s", elements.length, Arrays.toString(elements)));
				}

				int index = 0;
				long id = Integer.parseInt(elements[index++]);
				String firstName = elements[index++];
				String lastName = elements[index++];
				String street = elements[index++];
				String city = elements[index++];
				String postalCode = elements[index++];
				String phone = elements[index++];
				// should the email validation be performed here or in the customer class? I'm
				// leaning towards putting it here.
				String emailAddress = elements[index++];
				if (!Validator.validateEmail(emailAddress)) {
					throw new ApplicationException(String.format("Invalid email: %s", emailAddress));
				}
				String yyyymmdd = elements[index++];
				if (!Validator.validateJoinedDate(yyyymmdd)) {
					throw new ApplicationException(
							String.format("Invalid joined date: %s for customer %d", yyyymmdd, id));
				}
				int year = Integer.parseInt(yyyymmdd.substring(0, 4));
				int month = Integer.parseInt(yyyymmdd.substring(4, 6));
				int day = Integer.parseInt(yyyymmdd.substring(6, 8));

				Customer customer = new Customer.Builder(id, phone).setFirstName(firstName).setLastName(lastName)
						.setStreet(street).setCity(city).setPostalCode(postalCode).setEmailAddress(emailAddress)
						.setJoinedDate(year, month, day).build();
				customers.add(customer);
			}
		}
		return customers;
	}

	public static String sortCustomer(Purchase purchase) throws ApplicationException {
		String output = null;
		Purchase temp = purchase;
		int a = temp.getCustomerId();
		for (Customer i : customers) {
			if (i.getId() == a) {
				output = i.getFirstName() + " " + i.getLastName();
			}
		}
		return output;
	}

	public static ArrayList<Purchase> sortPurchaseByCustomerLastName(ArrayList<Purchase> purchases)
			throws ApplicationException, FileNotFoundException {
		ArrayList<Purchase> tempPurchases = new ArrayList<>();
		ArrayList<Purchase> purch = new ArrayList<>();
		ArrayList<Customer> tempCustomers = new ArrayList<>();
		boolean run;
		for (Purchase i : purchases) {
			run = true;
			int a = i.getCustomerId();
			for (Customer j : customers) {
				if ((j.getId() == a) && (run)) {
					tempCustomers.add(j);
					run = false;
				}
			}
		}
		tempCustomers.sort(new CustomerSorters.CompareByLastName());
		purch = purchases;
		for (Customer i : tempCustomers) {
			long b = i.getId();
			for (int j = 0; j < purch.size(); j++) {
				if (purch.get(j).getCustomerId() == b) {
					tempPurchases.add(purch.get(j));
					purch.remove(j);
				}
			}

		}
		return tempPurchases;
	}

	public static ArrayList<Purchase> sortPurchaseByCustomerLastNameDesc(ArrayList<Purchase> purchases)
			throws ApplicationException, FileNotFoundException {
		ArrayList<Purchase> tempPurchases = new ArrayList<>();
		ArrayList<Purchase> purch = new ArrayList<>();
		ArrayList<Customer> tempCustomers = new ArrayList<>();
		boolean run;
		for (Purchase i : purchases) {
			run = true;
			int a = i.getCustomerId();
			for (Customer j : customers) {
				if ((j.getId() == a) && (run)) {
					tempCustomers.add(j);
					run = false;
				}
			}
		}
		tempCustomers.sort(new CustomerSorters.CompareByLastNameDescending());
		purch = purchases;
		for (Customer i : tempCustomers) {
			long b = i.getId();
			for (int j = 0; j < purch.size(); j++) {
				if (purch.get(j).getCustomerId() == b) {
					tempPurchases.add(purch.get(j));
					purch.remove(j);
				}
			}

		}
		return tempPurchases;
	}

	public static ArrayList<Purchase> filterPurchaseByCustomerID(ArrayList<Purchase> purchases)
			throws ApplicationException, FileNotFoundException {
		ArrayList<Purchase> tempPurchases = new ArrayList<>();
		String a = BookOptions.getCustomerId();
		int b = Integer.parseInt(a);
		for (Purchase i : purchases) {
			if (i.getCustomerId() == b) {
				tempPurchases.add(i);
			}
		}
		return tempPurchases;
	}

}
