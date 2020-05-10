package a01038582.books2.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.database.BookDao;
import a01038582.books2.database.DbConstants;
import a01038582.books2.database.PurchaseDao;

public class PurchasesCountDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	private static final Logger LOG = LogManager.getLogger();

	BookDao bookDao;
	FilterByCustomerId filter;

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 *             if SQLException thrown
	 */
	public PurchasesCountDialog() throws SQLException {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 359, 151);
		setLocationRelativeTo(null);
		setTitle("Purchases Count");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 23, 66, 37, 72, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 25, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Total Purchases Count");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 2;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.anchor = GridBagConstraints.NORTH;
			gbc_textField.gridx = 3;
			gbc_textField.gridy = 2;
			contentPanel.add(textField, gbc_textField);
			try {
				String sql = getSqlStatement();
				String string = String.format("%.2f", PurchaseDao.getTotalOfPurchases(sql));
				textField.setText(string);
				MainFrame.setFilter(false);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
			textField.setEditable(false);
			textField.setColumns(10);
		}

	}

	private static String getSqlStatement() {
		if (MainFrame.getFilter()) {
			return "select SUM(price) from " + DbConstants.PURCHASE_TABLE_NAME + " WHERE customerId="
					+ FilterByCustomerId.getCustomerId();
		}

		return "select SUM(price) from " + DbConstants.PURCHASE_TABLE_NAME;
	}

}
