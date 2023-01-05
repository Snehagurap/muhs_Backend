package in.cdac.university.globalService.bean;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TempleHeadCompBean {
	
	private Long unumTempleId;
	 
	 private Set<HeadClass> headClass;
}
