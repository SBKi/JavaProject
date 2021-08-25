package vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class StudentVo {
	private String Student_No;
	private String Student_Name;
	private String Department;
	private String Address;
}
