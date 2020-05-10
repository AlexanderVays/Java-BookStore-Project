package a01038582.books2.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01038582.books2.BookStore;

public class DropDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the dialog.
	 */
	public DropDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Drop Dialog");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 72, 49, 0, 0, 0, 165, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 20, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Do you want to drop all tables from the database?");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 5;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton yesButton = new JButton("YES");
				yesButton.setActionCommand("YES");
				yesButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						LOG.debug("Dropping the tables from database if exist");
						try {
							BookStore.dropTablesFromMenu();
						} catch (Exception e) {
							LOG.error(e.getMessage());
						}

						try {
							BookStore.connection.close();
						} catch (SQLException e) {
							LOG.error(e.getMessage());
						} finally {
							LOG.info("Books2 has stopped");
							System.exit(0);
						}
					}
				});
				buttonPane.add(yesButton);
				getRootPane().setDefaultButton(yesButton);
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

}
