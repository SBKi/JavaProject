package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.DefaultTableModel;


import dao.StudentDao;
import vo.StudentVo;

public class StduentGui extends JFrame implements ActionListener, MouseListener {
	StudentDao st_dao = StudentDao.getInstance();
	JTextField stNo , stNa , stDe , stAd ;
	JButton bt_Search , bt_Add  , bt_Modify  , bt_Delete ;
	DefaultTableModel dm;
	JTable table;
	JScrollPane tableScroll;
	
	private JPanel contentPane;
	
	public StduentGui() {
		setTitle("Student Management");
		createMenu(); // 메뉴바
		createComponent();

		setSize(800, 600); // 화면 사이즈
		setResizable(false); // 화면 크기고정
		setLocationRelativeTo(null);// 화면 중앙표시
		setLayout(null); // 레이아웃 커스텀 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 정상종료
		setVisible(true);	//화면 출력
	}

	private void createComponent() {	// 컴포넌트 관리

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(30, 18, 592, 135);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student No");
		lblNewLabel.setBounds(12, 10, 100, 21);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Student_Name");
		lblNewLabel_1.setBounds(12, 40, 100, 21);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Department");
		lblNewLabel_2.setBounds(12, 71, 100, 21);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Address");
		lblNewLabel_3.setBounds(12, 102, 100, 21);
		panel.add(lblNewLabel_3);
		
		stNo = new JTextField();
		stNo.setBounds(112, 10, 345, 21);
		panel.add(stNo);
		stNo.setColumns(10);
		stNo.setFocusTraversalKeysEnabled(false);
		stNo.addKeyListener(new KeyAdapter() { 
		    public void keyPressed(KeyEvent e) { 
		     if(e.getKeyCode() == KeyEvent.VK_TAB)
		    	 stNa.requestFocus();
		    }
		 } 
		);
		
		stNa = new JTextField();
		stNa.setBounds(112, 40, 345, 21);
		panel.add(stNa);
		stNa.setColumns(10);
		stNa.setFocusTraversalKeysEnabled(false);
		stNa.addKeyListener(new KeyAdapter() { 
		    public void keyPressed(KeyEvent e) { 
		     if(e.getKeyCode() == KeyEvent.VK_TAB)
		    	 stDe.requestFocus();
		    }
		 } 
		);
		
		stDe = new JTextField();
		stDe.setBounds(112, 71, 345, 21);
		panel.add(stDe);
		stDe.setColumns(10);
		stDe.setFocusTraversalKeysEnabled(false);
		stDe.addKeyListener(new KeyAdapter() { 
		    public void keyPressed(KeyEvent e) { 
		     if(e.getKeyCode() == KeyEvent.VK_TAB)
		    	 stAd.requestFocus();
		    }
		 } 
		);
		
		stAd = new JTextField();
		stAd.setBounds(112, 102, 345, 21);
		panel.add(stAd);
		stAd.setColumns(10);
		stAd.setFocusTraversalKeysEnabled(false);
		stAd.addKeyListener(new KeyAdapter() { 
		    public void keyPressed(KeyEvent e) { 
		     if(e.getKeyCode() == KeyEvent.VK_TAB)
		    	 bt_Search.requestFocus();
		    }
		 } 
		);
		
		bt_Search = new JButton("Search");
		bt_Search.setBounds(469, 10, 111, 113);
		panel.add(bt_Search);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(634, 18, 123, 135);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		bt_Add = new JButton("Add");
		bt_Add.setBounds(7, 10, 104, 30);
		panel_1.add(bt_Add);
		
		bt_Modify = new JButton("Modify");
		bt_Modify.setBounds(7, 50, 104, 30);
		panel_1.add(bt_Modify);
		
		bt_Delete = new JButton("Delete");
		bt_Delete.setBounds(7, 90, 104, 30);
		panel_1.add(bt_Delete);

		//테이블 관련 ------------------------------------------
		table = new JTable();
		String header[] = { "Student_No", "Student_Name", "Department", "Address" };
		
		dm = new DefaultTableModel(null, header) {			//셀 내용 수정불가
			 public boolean isCellEditable(int row, int column){
				    return false;
		}};
		
		table = new JTable(dm);
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(400);
		table.getTableHeader().setReorderingAllowed(false);	//이동불가
		table.getTableHeader().setResizingAllowed(false);;	// 크기조절 불가
		tableScroll = new JScrollPane(table); // 스크롤 패널에 테이블 추가
		getList();	//JTable 에 리스트 추가
		tableScroll.setBounds(30, 163, 727, 388);
		table.addMouseListener(this);
		add(tableScroll);
		//테이블 관련-------------------------------------------------
		

		
		
		stNo.addMouseListener(this);

		
		bt_Search.addActionListener(this);
		bt_Add.addActionListener(this);
		bt_Modify.addActionListener(this);
		bt_Delete.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {		// 버튼 클릭 엑션 리스너
		String BtnString = e.getActionCommand();
		System.out.println(BtnString);			//!!!!!!!!!확인용!!!!!!!!!!!
		switch (BtnString) {
		case "Search":
			searchStudent();
				blankText();
			break;
		case "Add":
			if(addStudent()) {
				//추가 성공
				JOptionPane.showMessageDialog(null, "Sucess !!");
				blankText();
				getList();
			}else {
				//추가 실패
				JOptionPane.showMessageDialog(null, "Fail to add student !!\n 1) Please Fill in the blanks in the TextBox. \n 2) The same Student number is not allowed.");
				blankText();
				getList();
			}
			break;
		case "Modify":
			if(modifyStudent()) {
				//수정 성공
				JOptionPane.showMessageDialog(null, "Sucess !!");
				blankText();
				getList();
			}else {
				//수정 실패
				JOptionPane.showMessageDialog(null, "Fail to modify student !!\n 1) Please Check out the Student's name.");
				blankText();
				getList();
			}
			break;
		case "Delete":
			if(deleteStudent()) {
				//삭제 성공
				JOptionPane.showMessageDialog(null, "Sucess !!");
				blankText();
				getList();
			}else {
				//삭제 실패
				JOptionPane.showMessageDialog(null, "Fail to delete student !!\n 1) Please select a student from the list.\n 2) Students who borrowed books cannot be deleted.\n 3) You don't want to delete.");
				blankText();
				getList();
			}
			break;
		}

	}
	
	public boolean addStudent() {	//학생 추가
		boolean result = false;
		//텍스트 필드에 전부 공백없어야 가능
		if(stNo.getText().equals("") || stNa.getText().equals("") || stDe.getText().equals("")|| stAd.getText().equals(""))return result;
		result = st_dao.addStudent(new StudentVo(stNo.getText(),stNa.getText(),stDe.getText(),stAd.getText()));
		return result;
	}

	private boolean deleteStudent() {	// 학생 삭제
		boolean result = false;
		if(stNo.getText().equals(""))return false;
		
		int check =  JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?","Delete",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);	//삭제 확인 메시지창
		if(check == JOptionPane.YES_OPTION) {
			result = st_dao.deleteStudent(new StudentVo(stNo.getText(), stNa.getText(), stDe.getText(), stAd.getText()));
		}
		return result;
	}

	private boolean modifyStudent() {	// 학생 수정
		boolean result = false;
		if(stNa.getText().equals(""))return false;
		result = st_dao.modifyStudent(new StudentVo(stNo.getText(), stNa.getText(), stDe.getText(), stAd.getText()));
		return result;
	}

	private void searchStudent() {	// 학생 검색 (학번, 이름 둘중하나로 검색)
		if(stNo.getText().equals("") && stNa.getText().equals("") ) {	// 학번, 이름이 빈칸이면 전체목록 보여주기
			getList();
		}else {
			List<StudentVo> search_list =  st_dao.searchStudent(stNo.getText(), stNa.getText());
			
			for(int i=dm.getRowCount()-1;i>=0;i--) {	// 기존 리스트 지우기
				dm.removeRow(i);
			}
			String[] data = new String[4];
			for(StudentVo vo : search_list) {
				data[0] = vo.getStudent_No();
				data[1] = vo.getStudent_Name();
				data[2] = vo.getDepartment();
				data[3] = vo.getAddress();
				dm.addRow(data);
			}
		}
	}

	public void getList(){ // 리스트 불러오기
		stNo.setEditable(true);
		for(int i=dm.getRowCount()-1;i>=0;i--) {	// 기존 리스트 지우기
			dm.removeRow(i);
		}
		
		List<StudentVo> student_list = st_dao.getStudentList();
		String[] data = new String[4];
		for(StudentVo vo : student_list) {
			data[0] = vo.getStudent_No();
			data[1] = vo.getStudent_Name();
			data[2] = vo.getDepartment();
			data[3] = vo.getAddress();
			dm.addRow(data);
		}
	}
	
	public void blankText() {	// 텍스트 필드 초기화
		stNo.setText("");
		stNa.setText("");
		stDe.setText("");
		stAd.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {	// 마우스 클릭 리스너
		if(e.getSource().equals(table)){
			int row = table.getSelectedRow();
			stNo.setText((String) (table.getModel().getValueAt(row,0)));  
			stNa.setText((String) (table.getModel().getValueAt(row,1)));  
			stDe.setText((String) (table.getModel().getValueAt(row,2)));  
			stAd.setText((String) (table.getModel().getValueAt(row,3))); 
			stNo.setEditable(false);
		}else {
			blankText();
			stNo.setEditable(true);
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
	private void createMenu() {	//메뉴바 관리

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
	class MenuActionListener implements ActionListener {	// 메뉴 리스너
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
