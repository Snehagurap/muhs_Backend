package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
