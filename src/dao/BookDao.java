package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BookVo;

public class BookDao {
	private static BookDao dao = new BookDao();

	private BookDao() {
	}

	public static BookDao getInstance() {
		return dao;
	}

	// 책 전체 목록	(책 테이블전체에서 빌려간 책은 빼고 전부 검색)
	public List<BookVo> getBookList() {
		List<BookVo> list = new ArrayList<BookVo>();
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT B.BNO, B.BTITLE, B.BWRITER, B.BPUBLISHER, B.BPU_DATE \r\n" + "FROM BOOK B\r\n"
					+ "WHERE B.BNO NOT IN (SELECT b2.BNO FROM BORROW b2 WHERE b2.BNO IS NOT NULL)\r\n"
					+ "ORDER BY B.BNO ";
			// 대여 테이블에 북 no 있으면 안긁어오기
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new BookVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5)));
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 책 검색  (책번호와 책 이름 받아와서 빌려간책은 빼고 검색)
	public List<BookVo> searchBook(String b_no, String b_name) {
		List<BookVo> list = new ArrayList<BookVo>();
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String no = "";
			String name = "";
			if (!b_no.equals("")) {
				no = "%" + b_no + "%";
			}
			if (!b_name.equals("")) {
				name = "%" + b_name + "%";
			}

			String sql = "SELECT  B.BNO, B.BTITLE, B.BWRITER, B.BPUBLISHER, B.BPU_DATE \r" + "FROM BOOK B\r"
					+ "WHERE (UPPER(B.BNO) Like UPPER(?) OR UPPER(B.BTITLE) LIKE UPPER(?)) AND B.BNO NOT IN (SELECT b2.BNO FROM BORROW b2 WHERE b2.BNO IS NOT NULL)\r"
					+ "ORDER BY B.BNO ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new BookVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5)));
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 책 번호로 책제목 찾기
	public String Bno_to_Btitle(String b_no) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String b_title = "";
		try {
			String sql = "SELECT BTITLE FROM BOOK WHERE BNO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				b_title = rs.getString(1);
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b_title;
	}
	
	// 책 번호로 책검색
	public BookVo getOneBook(String b_no) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookVo vo = null;

		try {
			String sql = "SELECT * FROM BOOK WHERE BNO =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new BookVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
}
