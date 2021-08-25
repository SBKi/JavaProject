package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OCU {

	// 오라클 연결하는 메소드를 작성. - 리턴 타입 Connection
	public static Connection connect() {
		Connection conn = null;

		String url = "jdbc:oracle:thin:@localhost:1521:XE"; // 데이터베이스 URL - 디비버에서 확인가능합니다.
		String driver = "oracle.jdbc.driver.OracleDriver"; // 오라클 드라이버클래스(패키지명.클래스명)
		String user = "javapj"; // 접속할 계정 정보 - 계정이름
		String password = "1234"; // ㄴ 패스워드

		try {
			// 메모리에 데이터베이스 드라이버를 로드합니다.(로드-> 적재)
			Class.forName(driver);

			// DriverManager 클래스는 메모리에 로드된 드라이버 클래스를 관리하고
			// DriverManager 클래스의 메소드 getConnection 으로 데이터베이스 드라이버에 대한 Connection 구현객체를 생성
			// ㄴ 인터페이스 Connection 타입으로 참조합니다.
			conn = DriverManager.getConnection(url, user, password); // db 연결에 필요한 정보 인자로 전달

		} catch (ClassNotFoundException e) {
			System.out.println("데이터베이스 드라이버 로드에 문제가 생겼습니다. : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 및 사용에 문제가 생겼습니다. : " + e.getMessage());
		}

		return conn; // 생성된 Connection 객체 리턴합니다.
	}

	// 오라클 연결 Connection 객체 close 메소드 - 인자 타입 Connection
	public static void close(Connection conn) {
		if (conn != null) // conn 이 널이아닐때만 close 실행하도록 합니다.
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("데이터베이스 연결 종료에 문제가 생겼습니다. : " + e.getMessage());
			}
	}

}