package vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor

public class BookVo {
	private String bNo;
	private String bTitle;
	private String bWriter;
	private String bPublishing;
	private String bPu_Date;
}
