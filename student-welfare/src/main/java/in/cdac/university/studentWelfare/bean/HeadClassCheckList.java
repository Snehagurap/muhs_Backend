package in.cdac.university.studentWelfare.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HeadClassCheckList {
		private Integer headerId;
		private Long componentId;
		private List<CheckListItems> checkListItems;
}
