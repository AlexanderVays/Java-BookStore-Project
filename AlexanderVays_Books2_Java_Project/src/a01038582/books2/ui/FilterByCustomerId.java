package a01038582.books2.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.database.CustomerDao;
import a01038582.books2.database.DbConstants;

public class FilterByCustomerId extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTextField textField;
	boolean idExist = false;
	private static int x;

	public static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the dialog.
	 */
	public FilterByCustomerId() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Purchases search by Customer ID");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 72, 49, 0, 0, 0, 165, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 20, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Please insert Customer ID");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 5;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			textField = new JTextField();
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.anchor = GridBagConstraints.NORTHWEST;
			gbc_textField_1.gridx = 5;
			gbc_textField_1.gridy = 5;
			contentPanel.add(textField, gbc_textField_1);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						String input = textField.getText();
						if ((input != null) && (input.length() > 0)) {
							try {
								x = Integer.parseInt(input);
								LOG.debug("Valid input");
								idExist = false;
								List<String> ids = CustomerDao.geIds();
								for (int i = 0; i < ids.size(); i++) {
									if (input.equals(ids.get(i))) {
										idExist = true;
									}
								}
							} catch (NumberFormatException e) {
								LOG.error("input is not a number: " + e.getMessage());
								return;
							}

							if (getIdExist()) {
								new PurchasesByCustomerIdDialog(x);
							} else {
								LOG.error("No Customer found in database with entered Customer ID " + x);
								;
							}

						} else {
							MainFrame.setFilter(false);
							new PurchasesListDialog();
						}

						setVisible(false);
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						setVisible(false);
					}
				});
				buttonPane.add(cancelButton);
			}

		}
	}

	public String getPurchaseByCustomerSql() {
		return "select SUM(price) from " + DbConstants.PURCHASE_TABLE_NAME + " WHERE customerId IS " + getX();
	}

	public static int getCustomerId() {
		return x;
	}

	public boolean getIdExist() {
		return idExist;
	}

}
