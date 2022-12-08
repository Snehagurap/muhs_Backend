package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class TemplateHeaderSubHeaderBean {

	@ListColumn(omit = true)
	private Long unumTemplHeadId;

	@ListColumn(order = 4, name = "Header Text")
	@NotBlank(message = "Header Text is mandatory")
	private String ustrHeadPrintText;

	@ListColumn(omit = true)
	private Long unumTemplSubheadId;

	@ListColumn(order = 2, name = "Prefix Text")
	private String ustrSubheadPrintPrefixText;
	
	List<TemplateComponentItemBean> compItemBean;

}
