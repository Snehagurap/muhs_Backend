package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CheckListBean {
	
	private Long unumTempleId;
	private Integer unumChecklistId;
	private String ustrChecklistName;
	private List<HeadClassCheckList> headClass;

	private List<CheckListItems> checkListItems;			// Used for displaying checklist items on application form

}
