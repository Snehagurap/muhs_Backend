package in.cdac.university.globalService.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckListBean {
	
	private Long unumTempleId;
	private Integer unumChecklistId;
	private String ustrChecklistName;
	private List<HeadClassCheckList> headClass;

}
