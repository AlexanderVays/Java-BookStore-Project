package a01038582.books2.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.BookStore;
import a01038582.books2.data.Customer;
import a01038582.books2.database.CustomerDao;
import a01038582.books2.database.Database;

public class CustomerDetails extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField textField_0;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

	public static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the dialog
	 * 
	 * @param customer
	 *            the Customer object
	 */
	public CustomerDetails(Customer customer) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 500, 350);

		JLabel lblNewLabel = new JLabel("ID");

		JLabel lblNewLabel_1 = new JLabel("First Name");

		JLabel lblNewLabel_2 = new JLabel("Last Name");

		JLabel lblNewLabel_3 = new JLabel("Street");

		JLabel lblNewLabel_4 = new JLabel("City");

		JLabel lblNewLabel_5 = new JLabel("Postal Code");

		JLabel lblNewLabel_6 = new JLabel("Phone");

		JLabel lblNewLabel_7 = new JLabel("Email");

		JLabel lblNewLabel_8 = new JLabel("Joined Date");
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					setVisible(false);
				}
			});
		}

		textField_0 = new JTextField();
		textField_0.setText(Long.toString(customer.getId()));
		textField_0.setEditable(false);
		textField_0.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setText(customer.getFirstName());
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setText(customer.getLastName());
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setText(customer.getStreet());
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setText(customer.getCity());
		textField_4.setColumns(10);

		textField_5 = new JTextField();
		textField_5.setText(customer.getPostalCode());
		textField_5.setColumns(10);

		textField_6 = new JTextField();
		textField_6.setText(customer.getPhone());
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		textField_7.setText(customer.getEmailAddress());
		textField_7.setColumns(10);

		textField_8 = new JTextField();
		textField_8.setText(String.valueOf(customer.getJoinedDate()));
		textField_8.setColumns(10);
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					Database database = BookStore.database;
					CustomerDao cusDao = new CustomerDao(database);

					int year = Integer.parseInt(textField_8.getText().substring(0, 4));
					int month = Integer.parseInt(textField_8.getText().substring(6, 7));
					int day = Integer.parseInt(textField_8.getText().substring(9, 10));

					Customer customer = new Customer.Builder(Long.valueOf(textField_0.getText()), textField_6.getText())
							.setFirstName(textField_1.getText()).setLastName(textField_2.getText())
							.setStreet(textField_3.getText()).setCity(textField_4.getText())
							.setPostalCode(textField_5.getText()).setEmailAddress(textField_7.getText())
							.setJoinedDate(year, month, day).build();
					try {
						cusDao.update(customer);
					} catch (SQLException e) {
						LOG.error(e.getMessage());
					}
					setVisible(false);
				}
			});
		}
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(255).addComponent(okButton).addGap(18)
								.addComponent(cancelButton))
						.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblNewLabel_8, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 72,
												Short.MAX_VALUE))
								.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_5)
								.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 49,
										GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_1)
										.addComponent(textField_0, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
										.addComponent(textField_7).addComponent(textField_6).addComponent(textField_5)
										.addComponent(textField_2).addComponent(textField_3)
										.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
										.addComponent(textField_8))))
				.addContainerGap(108, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						textField_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(
						textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2).addComponent(
						textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_4).addComponent(
						textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_3).addComponent(
						textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_5).addComponent(
						textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_6).addComponent(
						textField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_7).addComponent(
						textField_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_8).addComponent(
						textField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(okButton)
						.addComponent(cancelButton))
				.addContainerGap(24, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
	}
}
