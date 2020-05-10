package a01038582.books2.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.database.PurchaseDao;

public class PurchasesByCustomerIdDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	public static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the dialog.
	 * 
	 * @param x
	 *            the customer id
	 */
	public PurchasesByCustomerIdDialog(Integer x) {
		setBounds(100, 100, 800, 740);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle("Purchases List by Customer ID");
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 988, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			try {
				JList<Object> purchasesList = new JList<>(PurchaseDao.getPurchasesByCustomerId(x));
				Font defaultListFont = purchasesList.getFont();
				purchasesList.setFont(new Font("monospaced", defaultListFont.getStyle(), defaultListFont.getSize()));
				JScrollPane scrollingList = new JScrollPane(purchasesList);
				GridBagConstraints gbc_list = new GridBagConstraints();
				gbc_list.insets = new Insets(0, 0, 5, 5);
				gbc_list.fill = GridBagConstraints.BOTH;
				gbc_list.gridx = 2;
				gbc_list.gridy = 1;
				contentPanel.add(scrollingList, gbc_list);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

}
