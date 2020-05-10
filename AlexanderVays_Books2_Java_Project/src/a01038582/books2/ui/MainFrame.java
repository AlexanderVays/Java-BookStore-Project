package a01038582.books2.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.BookStore;
import a01038582.books2.database.Database;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger LOG = LogManager.getLogger();

	private static boolean byAuthor = false;
	private static boolean booksDesc = false;
	private static boolean byJoinDate = false;
	private static boolean byLastName = false;
	private static boolean byTitle = false;
	private static boolean purchasesDesc = false;
	private static boolean filter = false;

	BookStore bookStore;

	/**
	 * Create the frame.
	 * 
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		JMenuBar mainMenuBar = new JMenuBar();
		setJMenuBar(mainMenuBar);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		mainMenuBar.add(fileMenu);
		JMenu booksMenu = new JMenu("Books");
		booksMenu.setMnemonic('B');
		mainMenuBar.add(booksMenu);
		JMenu customersMenu = new JMenu("Customers");
		customersMenu.setMnemonic('C');
		mainMenuBar.add(customersMenu);
		JMenu purchasesMenu = new JMenu("Purchases");
		purchasesMenu.setMnemonic('P');
		mainMenuBar.add(purchasesMenu);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		mainMenuBar.add(helpMenu);
		setVisible(true);

		/* File Menu */
		JMenuItem drop = new JMenuItem("Drop");
		drop.setMnemonic(KeyEvent.VK_D);
		drop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new DropDialog();
			}
		});
		fileMenu.add(drop);
		fileMenu.addSeparator();

		JMenuItem quit = new JMenuItem("Quit");
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Database.shutdown();
				LOG.info("Books2 has stopped");
				System.exit(0);
			}
		});
		fileMenu.add(quit);

		/* Books Menu */
		JMenuItem booksCount = new JMenuItem("Count");
		booksCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				LOG.debug("Books count");
				try {
					new BooksCountDialog();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}

			}
		});
		booksMenu.add(booksCount);
		booksMenu.addSeparator();

		JCheckBox booksCheckBox_1 = new JCheckBox("By Author");
		booksCheckBox_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (booksCheckBox_1.isSelected()) {
					byAuthor = true;
				} else {
					byAuthor = false;
				}
			}
		});
		booksMenu.add(booksCheckBox_1);

		JCheckBox booksCheckBox_2 = new JCheckBox("Descending");
		booksCheckBox_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (booksCheckBox_2.isSelected()) {
					booksDesc = true;
				} else {
					booksDesc = false;
				}
			}
		});
		booksMenu.add(booksCheckBox_2);
		booksMenu.addSeparator();

		JMenuItem booksList = new JMenuItem("List");
		booksList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				LOG.debug("List of the books");
				try {
					new BooksListDialog();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}

			}
		});
		booksMenu.add(booksList);

		/* Customers Menu */
		JMenuItem customersCount = new JMenuItem("Count");
		customersCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				LOG.debug("Customers count");
				try {
					new CustomersCountDialog();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}

			}
		});
		customersMenu.add(customersCount);
		customersMenu.addSeparator();

		JCheckBox customersCheckBox = new JCheckBox("By Join Date");
		customersCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (customersCheckBox.isSelected()) {
					byJoinDate = true;
				} else {
					byJoinDate = false;
				}
			}
		});
		customersMenu.add(customersCheckBox);
		customersMenu.addSeparator();

		JMenuItem customersList = new JMenuItem("List");
		customersList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				LOG.debug("List of the customers");
				try {
					new CustomersListDialog();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}

			}
		});
		customersMenu.add(customersList);

		/* Purchases Menu */
		JMenuItem total = new JMenuItem("Total");
		total.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				LOG.debug("Total of purchases");
				try {
					new PurchasesCountDialog();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}

			}
		});
		purchasesMenu.add(total);
		purchasesMenu.addSeparator();

		JCheckBox purchasesCheckBox_1 = new JCheckBox("By Last Name");
		purchasesCheckBox_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (purchasesCheckBox_1.isSelected()) {
					byLastName = true;
				} else {
					byLastName = false;
				}
			}
		});
		purchasesMenu.add(purchasesCheckBox_1);

		JCheckBox purchasesCheckBox_2 = new JCheckBox("By Title");
		purchasesCheckBox_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (purchasesCheckBox_2.isSelected()) {
					byTitle = true;
				} else {
					byTitle = false;
				}
			}
		});
		purchasesMenu.add(purchasesCheckBox_2);

		JCheckBox purchasesCheckBox_3 = new JCheckBox("Descending");
		purchasesCheckBox_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (purchasesCheckBox_3.isSelected()) {
					purchasesDesc = true;
				} else {
					purchasesDesc = false;
				}
			}
		});
		purchasesMenu.add(purchasesCheckBox_3);
		purchasesMenu.addSeparator();

		JMenuItem filterByCustomer = new JMenuItem("Filter by Customer ID");
		filterByCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				filter = true;
				LOG.debug("Filter by Customer ID");
				try {
					new FilterByCustomerId();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		purchasesMenu.add(filterByCustomer);
		purchasesMenu.addSeparator();

		JMenuItem purchasesList = new JMenuItem("List");
		purchasesList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				LOG.debug("List the purchases");
				try {
					new PurchasesListDialog();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}

			}
		});
		purchasesMenu.add(purchasesList);

		/* Help Menu */
		JMenuItem about = new JMenuItem("About", KeyEvent.VK_F1);
		about.setAccelerator(KeyStroke.getKeyStroke("F1"));
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showMessageDialog(MainFrame.this,
						"Application Assignment2 (Books2) \nBy Alexander Vays Plotnikov A01038582\nThis assignment continues on from Assignment1 (Books) and reinforces "
								+ "\nthe concepts and features of the java framework by adding data \npersistence and a graphical user interface for the Books app. ",
						"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(about);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Database.shutdown();
				LOG.info("Books2 has stopped");
				System.exit(0);
			}
		});
	}

	/**
	 * @return true or false of By Author option
	 */
	public static boolean getByAuthor() {
		return byAuthor;
	}

	/**
	 * @return true or false of Descending option
	 */
	public static boolean getBooksDesc() {
		return booksDesc;
	}

	/**
	 * @return true or false of By Join Date option
	 */
	public static boolean getByJoinDate() {
		return byJoinDate;
	}

	/**
	 * @return true or false of By LastName option
	 */
	public static boolean getByLastName() {
		return byLastName;
	}

	/**
	 * @return true or false of By Title option
	 */
	public static boolean getByTitle() {
		return byTitle;
	}

	/**
	 * @return true or false of Purchases Descending option
	 */
	public static boolean getPurchasesDesc() {
		return purchasesDesc;
	}

	/**
	 * @return filter the boolean
	 */
	public static boolean getFilter() {
		return filter;
	}

	public static void setFilter(boolean sts) {
		filter = sts;
	}

}
