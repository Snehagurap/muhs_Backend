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
public class HeadClassCheckList {
		private Integer headerId;
		private Long componentId;
		private List<CheckListItems> checkListItems;
}
