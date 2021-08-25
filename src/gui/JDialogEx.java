package gui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.BorrowDao;
import dao.StudentDao;
import vo.StudentVo;

public class JDialogEx extends JDialog {
	StudentDao s_dao = StudentDao.getInstance();
	BorrowDao br_dao = BorrowDao.getInstance();
	private JTextField textField;
	JLabel lbSno;
	JLabel lbSna;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			JDialogEx dialog = new JDialogEx("aa", "aa");
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
// TEST
	/**
	 * Create the dialog.
	 */
	public JDialogEx() {
		// TODO Auto-generated constructor stub
	}

	public JDialogEx(String b_no, String b_name,Book_Bo_ReGUI t) {
		setTitle("Borrow Check");
		setBounds(100, 100, 416, 300);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(223, 220, 165, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Borrow");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						boolean result = br_dao.insertBorrow(b_no, lbSno.getText());
						if (result) {
							JOptionPane.showMessageDialog(null, "Sucess !!");
							t.getList();	//	리스트 새로고침 
						} else {
							JOptionPane.showMessageDialog(null, "Fail to Borrow !!");
						}
						JDialogEx.this.dispose();
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JDialogEx.this.dispose();
					}
				});
				buttonPane.add(cancelButton);

			}
		}
		{
			JLabel lblNewLabel = new JLabel("Book_No        :");
			lblNewLabel.setBounds(12, 23, 90, 15);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Book_Name    :");
			lblNewLabel_1.setBounds(12, 52, 90, 15);
			getContentPane().add(lblNewLabel_1);
		}
		{
			JLabel lbBno = new JLabel(b_no);
			lbBno.setBounds(126, 23, 57, 15);
			getContentPane().add(lbBno);
		}
		{
			JLabel lbBna = new JLabel(b_name);
			lbBna.setBounds(126, 52, 200, 15);
			getContentPane().add(lbBna);
		}

		textField = new JTextField("");
		textField.setBounds(114, 104, 165, 21);
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Student_No     :");
		lblNewLabel_4.setBounds(12, 107, 90, 15);
		getContentPane().add(lblNewLabel_4);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(291, 103, 97, 23);
		getContentPane().add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				StudentVo vo = s_dao.getStudent(textField.getText());
				lbSno.setText(vo.getStudent_No());
				lbSna.setText(vo.getStudent_Name());
			};
		});

		JLabel lblNewLabel_5 = new JLabel("Student_No     :");
		lblNewLabel_5.setBounds(12, 154, 90, 15);
		getContentPane().add(lblNewLabel_5);

		lbSno = new JLabel(" ");
		lbSno.setBounds(126, 154, 57, 15);
		getContentPane().add(lbSno);

		JLabel lblNewLabel_7 = new JLabel("Student_Name :");
		lblNewLabel_7.setBounds(12, 189, 90, 15);
		getContentPane().add(lblNewLabel_7);

		lbSna = new JLabel(" ");
		lbSna.setBounds(126, 189, 100, 15);
		getContentPane().add(lbSna);
	}
}
