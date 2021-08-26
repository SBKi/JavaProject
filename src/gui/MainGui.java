package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import dao.BorrowDao;

public class MainGui extends JFrame implements ActionListener {
	BorrowDao br_dao = BorrowDao.getInstance();
	private JTextField id;
	private JPasswordField pw;
	JButton login;
	JMenu bookMenu,studentMenu;
	
	public static void main(String[] args) {
		new MainGui();
	}

	MainGui() {
		setTitle("Book & Student Management Program");
		
		createMenu(); // 메뉴바
		createComponent(); // 컴포넌트 생성
		br_dao.updateState();	// 대여테이블 연체상태 업데이트
		
		setSize(800, 600); // 화면 사이즈
		setResizable(false); // 화면 크기고정
		setLocationRelativeTo(null);// 화면 중앙표시
		setLayout(null); // 레이아웃 커스텀 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 정상종료
		setVisible(true); // 화면 표시
	}
	private void createComponent() {	// 컴포넌트 관리
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(40, 160, 700, 300);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Admin ID  :");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_1.setBounds(71, 96, 100, 30);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password :");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_2.setBounds(71, 164, 100, 30);
		panel.add(lblNewLabel_2);
		
		id = new JTextField();
		id.setBounds(207, 99, 187, 27);
		id.setFocusTraversalKeysEnabled(false);
		id.addKeyListener(new KeyAdapter() { 
		    public void keyPressed(KeyEvent e) { 
		     if(e.getKeyCode() == KeyEvent.VK_TAB)
		      pw.requestFocus();
		    }
		 } 
		);

		panel.add(id);
		id.setColumns(10);
		
		pw = new JPasswordField();
		pw.setBounds(207, 167, 187, 27);
		panel.add(pw);
		pw.setColumns(10);
		pw.setFocusTraversalKeysEnabled(false);
		pw.addKeyListener(new KeyAdapter() { 
		    public void keyPressed(KeyEvent e) { 
		     if(e.getKeyCode() == KeyEvent.VK_TAB)
		      login.requestFocus();
		    }
		 } 
		);
		
		login = new JButton("Login");
		login.setBounds(450, 96, 140, 96);
		panel.add(login);
		
		JLabel lblNewLabel = new JLabel("Book & Student Management Program");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel.setBounds(40, 87, 700, 63);
		getContentPane().add(lblNewLabel);		
		login.addActionListener(this);
	}
	private void createMenu() {	//메뉴바 관리

		JMenuBar mb = new JMenuBar(); // 메뉴바 생성
		bookMenu = new JMenu("Book");
		studentMenu = new JMenu("Student");

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
		
		 bookMenu.setEnabled(false);
	     studentMenu.setEnabled(false);

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(id.getText().equals("admin")&&pw.getText().equals("admin")) {
			JOptionPane.showMessageDialog(null, "Login Success");
			id.setEnabled(false);
			pw.setEnabled(false);
			login.setEnabled(false);
			bookMenu.setEnabled(true);
			studentMenu.setEnabled(true);
		}else {
			JOptionPane.showMessageDialog(null, "Login Fail !!");
			id.setText("");
			pw.setText("");
		}

	}

}
