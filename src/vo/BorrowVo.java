package vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class BorrowVo {
	private int Br_no;
	private String Student_No;
	private String bNo;
	private Date Br_date; // 대여일
	private Date Ex_date; // 반납 예정일 : 대여일 +3 일
	private String state = "F"; // 반납 상태 true : 연체상태
}