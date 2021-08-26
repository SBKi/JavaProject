package gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dao.BookDao;
import vo.BookVo;

public class Book_Bo_ReGUI extends JFrame implements ActionListener, MouseListener {
	BookDao b_dao = BookDao.getInstance();
	JPanel contentPane;
	JTextField bNo;
	JTextField bNa;
	JButton bt_Search;
	DefaultTableModel dm;
	JTable table;
	JScrollPane tableScroll;

	public Book_Bo_ReGUI() {
		setTitle("Borrow / Return");

		createMenu(); // 메뉴바
		createComponent(); // 컴포넌트 관리

		setSize(800, 600); // 화면 사이즈
		setResizable(false); // 화면 크기고정
		setLocationRelativeTo(null);// 화면 중앙표시
		setLayout(null); // 레이아웃 커스텀 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 정상종료
		setVisible(true);
	}

	private void createComponent() { // 컴포넌트 관리

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(39, 10, 710, 108);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Book_No");
		lblNewLabel.setBounds(12, 18, 96, 30);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Book_Name");
		lblNewLabel_1.setBounds(12, 58, 96, 30);
		panel.add(lblNewLabel_1);

		bNo = new JTextField();
		bNo.setBounds(95, 18, 418, 30);
		panel.add(bNo);
		bNo.setColumns(10);

		bNa = new JTextField();
		bNa.setBounds(95, 58, 418, 31);
		panel.add(bNa);
		bNa.setColumns(10);

		bt_Search = new JButton("Search");
		bt_Search.setBounds(525, 17, 159, 72);
		panel.add(bt_Search);

		table = new JTable();
		String header[] = { "Book_no", "Book_Name", "Writer", "Publisher", "Year of publication" };

		dm = new DefaultTableModel(null, header) { // 셀 내용 수정불가
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(dm);
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getTableHeader().setReorderingAllowed(false); // 이동불가
		table.getTableHeader().setResizingAllowed(false); // 크기조절 불가
		tableScroll = new JScrollPane(table); // 스크롤 패널에 테이블 추가
		getList(); // JTable 에 리스트 추가
		tableScroll.setBounds(39, 138, 710, 370);
		add(tableScroll);

		bNo.addMouseListener(this);
		bNa.addMouseListener(this);
		table.addMouseListener(this); // 리스트 선택 리스너
		bt_Search.addActionListener(this);

	}

	private void searchBook() { // 책 검색 (책번호, 책제목 둘중하나로 검색)

		if (bNo.getText().equals("") && bNa.getText().equals("")) { // 책번호, 책제목 이 빈칸이면 전체목록 보여주기
			getList();
		} else {
			List<BookVo> search_list = b_dao.searchBook(bNo.getText(), bNa.getText());

			for (int i = dm.getRowCount() - 1; i >= 0; i--) { // 기존 리스트 지우기
				dm.removeRow(i);
			}
			String[] data = new String[5];
			for (BookVo vo : search_list) {
				data[0] = vo.getBNo();
				data[1] = vo.getBTitle();
				data[2] = vo.getBWriter();
				data[3] = vo.getBPublishing();
				String year = vo.getBPu_Date().substring(0, 4);
				String month = vo.getBPu_Date().substring(4, 6);
				String day = vo.getBPu_Date().substring(6);
				data[4] = year + "-" + month + "-" + day;
				dm.addRow(data);
			}
		}
	}

	public void getList() { // 리스트 불러오기

		for (int i = dm.getRowCount() - 1; i >= 0; i--) { // 기존 리스트 지우기
			dm.removeRow(i);
		}

		List<BookVo> book_list = b_dao.getBookList();
		String[] data = new String[5];
		for (BookVo vo : book_list) {
			data[0] = vo.getBNo();
			data[1] = vo.getBTitle();
			data[2] = vo.getBWriter();
			data[3] = vo.getBPublishing();
			String year = vo.getBPu_Date().substring(0, 4);
			String month = vo.getBPu_Date().substring(4, 6);
			String day = vo.getBPu_Date().substring(6);
			data[4] = year + "-" + month + "-" + day;
			dm.addRow(data);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 버튼 클릭 엑션 리스너
		String BtnString = e.getActionCommand();
		if (BtnString.equals("Search")) {
			searchBook();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) { // 마우스 클릭 리스너
		if (e.getSource().equals(table)) {
			int row = table.getSelectedRow();
			bNo.setText((String) (table.getModel().getValueAt(row, 0)));
			bNa.setText((String) (table.getModel().getValueAt(row, 1)));
			// 새창
			JTable t = (JTable) e.getSource();
			if (e.getClickCount() == 2) {
				TableModel m = t.getModel();
				Point pt = e.getPoint();
				int i = t.rowAtPoint(pt);
				if (i >= 0) {
					row = t.convertRowIndexToModel(i);
					String no, name;
					no = String.valueOf(m.getValueAt(row, 0));
					name = String.valueOf(m.getValueAt(row, 1));
					JDialogEx dialog = new JDialogEx(no, name, this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			}
		} else if (e.getSource().equals(bNo)) {
			bNo.setEnabled(true);
			bNa.setText("");
			bNa.setEnabled(false);
		} else {
			bNa.setEnabled(true);
			bNo.setText("");
			bNo.setEnabled(false);
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
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
}
