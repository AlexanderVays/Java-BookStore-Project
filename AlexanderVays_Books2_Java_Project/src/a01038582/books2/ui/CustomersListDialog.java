package a01038582.books2.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.BookStore;
import a01038582.books2.data.Customer;
import a01038582.books2.database.CustomerDao;
import a01038582.books2.database.Database;

public class CustomersListDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	public static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the dialog.
	 */
	public CustomersListDialog() {
		setBounds(100, 100, 800, 740);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle("Customers List");
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 988, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			try {
				JList<Object> customersList = new JList<>(CustomerDao.getCustomersList());
				customersList.setFont(new Font("Monospaced", Font.BOLD, 14));
				// Font defaultListFont = customersList.getFont();
				// customersList.setFont(new Font("monospaced", defaultListFont.getStyle(),
				// defaultListFont.getSize()));
				JScrollPane scrollingList = new JScrollPane(customersList);
				GridBagConstraints gbc_list = new GridBagConstraints();
				gbc_list.insets = new Insets(0, 0, 5, 5);
				gbc_list.fill = GridBagConstraints.BOTH;
				gbc_list.gridx = 2;
				gbc_list.gridy = 1;
				contentPanel.add(scrollingList, gbc_list);
				customersList.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent evt) {
						String selected = (String) customersList.getSelectedValue();
						Long id = CustomerDao.getCustomerId(selected);
						Database database = BookStore.database;
						CustomerDao cusDao = new CustomerDao(database);
						Customer customer = cusDao.getCustomer(id);
						new CustomerDetails(customer);
					}
				});
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

}
