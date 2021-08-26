package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BorrowVo;

public class BorrowDao {

	private static BorrowDao dao = new BorrowDao();

	private BorrowDao() {
	}

	public static BorrowDao getInstance() {

		return dao;

	}

	// 대여 하기
	public boolean insertBorrow(String B_no, String Student_no) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;

		try {

			String sql = "INSERT INTO BORROW VALUES(BORROW_SEQ.NEXTVAL,?,?,sysdate,sysdate+3,'F')"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Student_no);
			pstmt.setString(2, B_no);
			pstmt.execute();

			pstmt.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 대여 목록 전체 불러오기
	public List<BorrowVo> getAllList() {
		List<BorrowVo> list = new ArrayList<BorrowVo>();
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM BORROW";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new BorrowVo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5),
						rs.getString(6)));
			}

			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// return 버튼
	public void delBorrow(String no) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;

		try {
			String sql = "DELETE FROM Borrow WHERE Br_no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.execute();

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 검색 리스트
	public List<BorrowVo> searchBorrow(String stu_no) {
		List<BorrowVo> list = new ArrayList<BorrowVo>();
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			if (stu_no.equals("")) {
				sql = "SElECT * FROM BORROW ";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
			} else {
				sql = "SElECT * FROM BORROW WHERE UPPER(STUDENT_NO) LIKE UPPER(?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, stu_no);
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				list.add(new BorrowVo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5),
						rs.getString(6)));
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

	// 연체 상태 업데이트
	public void updateState() {
		Connection conn = OCU.connect();
		String sql = "UPDATE BORROW SET STATE = 'T' WHERE EX_DATE < SYSDATE";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 대여 데이터 추출
	public BorrowVo getBorrow(String st_no, String bo_no) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BorrowVo vo = null;
		
		try {
			String sql = "SELECT * FROM BORROW WHERE STUDENT_NO=? AND BNO=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, st_no);
			pstmt.setString(2, bo_no);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				vo = new BorrowVo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5),
						rs.getString(6));
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

	// 연체 상태의 데이터 추출
	public List<BorrowVo> stateT_Borrow() {
		List<BorrowVo> list = new ArrayList<BorrowVo>();
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SElECT * FROM BORROW	WHERE STATE = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "T");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new BorrowVo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5),
						rs.getString(6)));
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
}
