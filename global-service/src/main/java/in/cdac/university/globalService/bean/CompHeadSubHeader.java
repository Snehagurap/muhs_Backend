package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CompHeadSubHeader {

	private Long unumTempleCompId;

	private Long unumTempleHeadId;

	private Long unumTempleSubheadId;
	
	private String ustrCompPrintText;
	
	private String ustrHeadPrintText;
	
	private String ustrSubheadPrintText;
	
    private Double unumHeaderOrderNo;
    
    private Double unumComponentOrderNo;

	private String ustrTempledtlDescription;
	
	List<TemplateMasterDtlsChildBean> children;

	@Override
	public int hashCode() {
		return Objects.hash(unumTempleCompId, unumTempleHeadId, unumTempleSubheadId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompHeadSubHeader other = (CompHeadSubHeader) obj;
		return Objects.equals(unumTempleCompId, other.unumTempleCompId)
				&& Objects.equals(unumTempleHeadId, other.unumTempleHeadId)
				&& Objects.equals(unumTempleSubheadId, other.unumTempleSubheadId);
	}

}
