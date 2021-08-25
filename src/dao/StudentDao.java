package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.StudentVo;

public class StudentDao {
	private static StudentDao dao = new StudentDao();

	private StudentDao() {
	}

	public static StudentDao getInstance() {
		return dao;
	}

	// 학생 목록
	public List<StudentVo> getStudentList() {
		List<StudentVo> list = new ArrayList<StudentVo>();
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM STUDENT";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new StudentVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
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

	// 학생 등록
	public boolean addStudent(StudentVo vo) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO STUDENT VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getStudent_No());
			pstmt.setString(2, vo.getStudent_Name());
			pstmt.setString(3, vo.getDepartment());
			pstmt.setString(4, vo.getAddress());
			pstmt.execute();

			pstmt.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	// 학생 검색
	public List<StudentVo> searchStudent(String st_no, String st_name) {
		List<StudentVo> list = new ArrayList<StudentVo>();
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String no = "";
			String name = "";
			if (!st_no.equals("")) {
				no = "%" + st_no + "%";
			}
			if (!st_name.equals("")) {
				name = "%" + st_name + "%";
			}

			String sql = "SELECT * FROM STUDENT WHERE UPPER(STUDENT_NO) Like UPPER(?) OR UPPER(STUDENT_NAME) LIKE UPPER(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(new StudentVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
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

	// 학생 수정
	public boolean modifyStudent(StudentVo vo) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE STUDENT SET STUDENT_NAME = ?,DEPARTMENT =?,Address=? WHERE STUDENT_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getStudent_Name());
			pstmt.setString(2, vo.getDepartment());
			pstmt.setString(3, vo.getAddress());
			pstmt.setString(4, vo.getStudent_No());
			pstmt.execute();

			pstmt.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	// 학생 삭제
	public boolean deleteStudent(StudentVo vo) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 학생 테이블있는 학번이 대출 테이블 학번에 있는지 확인
			String sql = "SELECT s.STUDENT_NO FROM STUDENT s , BORROW b WHERE s.STUDENT_NO = b.STUDENT_NO AND s.STUDENT_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getStudent_No());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				rs.close();
				return false; // 대출테이블에 학생번호가 존재하면 현제 책을 빌린상황이라 아이디 삭제 불가능
			} else {
				sql = "DELETE FROM STUDENT s WHERE STUDENT_NO = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getStudent_No());
				pstmt.execute();
			}

			pstmt.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// 학생 검색
	public StudentVo getStudent(String no) {
		Connection conn = OCU.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudentVo vo = null;

		try {
			String sql = "SELECT * FROM STUDENT WHERE Student_No =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new StudentVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
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