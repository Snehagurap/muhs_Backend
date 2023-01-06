package in.cdac.university.globalService.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckListItems {
	
	private String ustrChecklistItemName;
	private Integer unumChecklistItemOrderno;
	private Integer unumTempleItemId;

}
 