package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dao.BookDao;
import dao.BorrowDao;
import dao.StudentDao;
import vo.BookVo;
import vo.BorrowVo;
import vo.StudentVo;

public class Book_InfoGUI extends JFrame implements ActionListener, MouseListener {
	BorrowDao br_dao = BorrowDao.getInstance();
	BookDao b_dao = BookDao.getInstance();
	JLabel l1, l2;
	JLabel lbSN, lbBN, lbBD, lbRD, lbSNo, lbBNo;
	JTextField t1;
	JButton b1, b2;
	JTable table;
	DefaultTableModel dm;
	JScrollPane tableScroll;
	JPanel contentPane;
	JTextField textField;

	public Book_InfoGUI() {
		createMenu(); // 메뉴바
		createComponent(); // 컴포넌트 추가

		setSize(800, 400); // 화면 사이즈``
		setResizable(false); // 화면 크기고정
		setLocationRelativeTo(null);// 화면 중앙표시
		setLayout(null); // 레이아웃 커스텀 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 정상종료
		setVisible(true);
	}

	private void createComponent() { // 컴포넌트 관리

		l2 = new JLabel("::::: Borrow Status Table :::::::::");
		l2.setFont(new Font(null, Font.BOLD, 20));
		l2.setBounds(340, 10, 300, 30);
		b2 = new JButton("Overdue List");
		b2.setBounds(660, 10, 115, 29);
		b2.addActionListener(this);

		table = new JTable();
		String header[] = { "St_No", "Bo_No", "Book_Title", "Borrow_Date", "Exp_Date", "Statue" };

		dm = new DefaultTableModel(null, header) { // 셀 내용 수정불가
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(dm);
		table.getColumnModel().getColumn(0).setPreferredWidth(3);
		table.getColumnModel().getColumn(1).setPreferredWidth(3);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(45);
		table.getColumnModel().getColumn(5).setPreferredWidth(0);
		table.getColumnModel().getColumn(5).setMinWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		table.getTableHeader().setReorderingAllowed(false); // 이동불가
		table.getTableHeader().setResizingAllowed(false); // 크기조절 불가
		tableScroll = new JScrollPane(table); // 스크롤 패널에 테이블 추가
		tableScroll.setBounds(340, 50, 440, 280);
		getBorrowAllList(); // JTable 에 리스트 추가
		table.addMouseListener(this);

		MyRenderer myRenderer = new MyRenderer();
		table.setDefaultRenderer(Object.class, myRenderer);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(tableScroll);
		contentPane.add(l2);
		contentPane.add(b2);

		JLabel lblNewLabel = new JLabel("Student_No :");
		lblNewLabel.setBounds(12, 10, 103, 30);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(90, 15, 120, 21);
		contentPane.add(textField);

		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(222, 14, 97, 23);
		btnNewButton.addActionListener(this);
		contentPane.add(btnNewButton);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(12, 50, 307, 230);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel jl = new JLabel("Student No	:");
		jl.setBounds(12, 30, 120, 15);
		lbSNo = new JLabel();
		lbSNo.setBounds(112, 30, 120, 15);

		JLabel jl2 = new JLabel("Student Name:");
		jl2.setBounds(12, 60, 120, 15);
		lbSN = new JLabel();
		lbSN.setBounds(112, 60, 120, 15);

		JLabel jl3 = new JLabel("Book No		:");
		jl3.setBounds(12, 90, 283, 15);
		lbBNo = new JLabel();
		lbBNo.setBounds(112, 90, 283, 15);

		JLabel jl4 = new JLabel("Book Title	:");
		jl4.setBounds(12, 120, 283, 15);
		lbBN = new JLabel();
		lbBN.setBounds(112, 120, 200, 15);

		JLabel jl5 = new JLabel("Borrow Date	:");
		jl5.setBounds(12, 150, 150, 15);
		lbBD = new JLabel();
		lbBD.setBounds(112, 150, 150, 15);

		JLabel jl6 = new JLabel("Expiry Date	:");
		jl6.setBounds(12, 180, 150, 15);
		lbRD = new JLabel();
		lbRD.setBounds(112, 180, 150, 15);

		panel.add(lbSN);
		panel.add(lbBN);
		panel.add(lbBD);
		panel.add(lbRD);
		panel.add(lbBNo);
		panel.add(lbSNo);
		panel.add(jl);
		panel.add(jl2);
		panel.add(jl3);
		panel.add(jl4);
		panel.add(jl5);
		panel.add(jl6);

		JButton btnNewButton_1 = new JButton("Return Book");
		btnNewButton_1.setBounds(12, 287, 307, 43);
		btnNewButton_1.addActionListener(this);
		contentPane.add(btnNewButton_1);

	}

	private void getBorrowAllList() {
		SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Borrow Table 읽어오기
		List<BorrowVo> br_list = br_dao.getAllList();

		for (int i = dm.getRowCount() - 1; i >= 0; i--) { // 기존 리스트 지우기
			dm.removeRow(i);
		}
		String[] data = new String[6];
		for (BorrowVo vo : br_list) {
			data[0] = vo.getStudent_No();
			data[1] = vo.getBNo();
			data[2] = b_dao.Bno_to_Btitle(vo.getBNo());
			data[3] = setDateFormat.format(vo.getBr_date());
			data[4] = setDateFormat.format(vo.getEx_date());
			data[5] = vo.getState();
			dm.addRow(data);
		}

	}

	private void createMenu() { // 메뉴바 관리

		JMenuBar mb = new JMenuBar(); // 메뉴바 생성
		JMenu bookMenu = new JMenu("Book");
		JMenu studentMenu = new JMenu("Student");

		MenuActionListener listener = new MenuActionListener();

		JMenuItem bookMenuItem1 = new JMenuItem("Borrow");
		JMenuItem bookMenuItem2 = new JMenuItem("Borrow info / Return");
		JMenuItem studentMenuItem = new JMenuItem("Student");

		studentMenuItem.addActionListener(listener);
		bookMenuItem1.addActionListener(listener);
		bookMenuItem2.addActionListener(listener);

		bookMenu.add(bookMenuItem1);
		bookMenu.add(bookMenuItem2);
		studentMenu.add(studentMenuItem);

		mb.add(studentMenu);
		mb.add(bookMenu);
		setJMenuBar(mb); // 메뉴바를 프레임에 부착
	}

	class MenuActionListener implements ActionListener { // 메뉴 리스너
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			switch (cmd) { // 메뉴 아이템의 종류 구분
			case "Student":
				setVisible(false);
				new StduentGui();
				break;
			case "Borrow":
				setVisible(false);
				new Book_Bo_ReGUI();
				break;
			case "Borrow info / Return":
				setVisible(false);
				new Book_InfoGUI();
				break;
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) { // 액션 선태 길스너
		if (e.getActionCommand().equals("Search")) {
			SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Borrow Table 읽어오기
			List<BorrowVo> br_list = br_dao.searchBorrow(textField.getText());

			for (int i = dm.getRowCount() - 1; i >= 0; i--) { // 기존 리스트 지우기
				dm.removeRow(i);
			}
			String[] data = new String[6];
			for (BorrowVo vo : br_list) {
				data[0] = vo.getStudent_No();
				data[1] = vo.getBNo();
				data[2] = b_dao.Bno_to_Btitle(vo.getBNo());
				data[3] = setDateFormat.format(vo.getBr_date());
				data[4] = setDateFormat.format(vo.getEx_date());
				data[5] = vo.getState();
				dm.addRow(data);
			}

		} else if (e.getActionCommand().equals("Return Book")) {
			int check = JOptionPane.showConfirmDialog(null, "Are you sure you want to return this book?", "Delete",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE); // 삭제 확인 메시지창
			if (check == JOptionPane.YES_OPTION) {
				BorrowVo vo = br_dao.getBorrow(lbSNo.getText(), lbBNo.getText());
				br_dao.delBorrow(String.valueOf(vo.getBr_no()));
				getBorrowAllList();
				JOptionPane.showMessageDialog(null, "Book was returned.");
			}
		} else if (e.getActionCommand().equals("Overdue List")) {
			SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// Borrow Table 읽어오기
			List<BorrowVo> br_list = br_dao.stateT_Borrow();

			for (int i = dm.getRowCount() - 1; i >= 0; i--) { // 기존 리스트 지우기
				dm.removeRow(i);
			}
			String[] data = new String[6];
			for (BorrowVo vo : br_list) {
				data[0] = vo.getStudent_No();
				data[1] = vo.getBNo();
				data[2] = b_dao.Bno_to_Btitle(vo.getBNo());
				data[3] = setDateFormat.format(vo.getBr_date());
				data[4] = setDateFormat.format(vo.getEx_date());
				data[5] = vo.getState();
				dm.addRow(data);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(table)) {
			int row = table.getSelectedRow();
			StudentDao s_dao = StudentDao.getInstance();
			BookDao b_dao = BookDao.getInstance();
			StudentVo s_vo = s_dao.getStudent((String) (table.getModel().getValueAt(row, 0)));
			BookVo b_vo = b_dao.getOneBook((String) (table.getModel().getValueAt(row, 1)));
			BorrowVo br_vo = br_dao.getBorrow(s_vo.getStudent_No(), b_vo.getBNo());
			// 새창
			SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			lbSNo.setText(s_vo.getStudent_No());
			lbSN.setText(s_vo.getStudent_Name());
			lbBN.setText(b_vo.getBTitle());
			lbBNo.setText(String.valueOf(b_vo.getBNo()));
			lbBD.setText(setDateFormat.format(br_vo.getBr_date()));
			lbRD.setText(setDateFormat.format(br_vo.getEx_date()));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public class MyRenderer extends DefaultTableCellRenderer {// 연체 리스트 색상변경
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			TableModel data = table.getModel();
			String temp = (String) data.getValueAt(row, 5);

			if (!table.isRowSelected(row)) {
				if (temp.equals("T")) // 확인
					c.setBackground(new Color(255, 153, 153));
				else
					c.setBackground(table.getBackground());
			}

			return c;
		}

	}
}