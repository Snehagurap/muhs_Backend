package in.cdac.university.studentWelfare.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class TemplateComponentDtlsBean {
	
	
	private Long unumTempleCompItemId;

	private Integer unumIsvalid;

	private Date udtEffFrom;

	private Date udtEffTo;

	private Date udtEntryDate;

	private Long unumEntryUid;

	private Long unumTempleCompId;

	@NotNull(message = "unumTempleItemId is mandatory")
	private Long unumTempleItemId;

	private Integer unumUnivId;

	private String ustrDescription;
}
