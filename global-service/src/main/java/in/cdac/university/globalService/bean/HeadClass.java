package in.cdac.university.globalService.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeadClass {
	
	private Integer headerId;
	private Integer headerDisplayOrder;
	private List<Components> components;
	

}
