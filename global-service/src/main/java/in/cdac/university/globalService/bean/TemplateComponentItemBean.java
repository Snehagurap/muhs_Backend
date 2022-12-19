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
public class TemplateComponentItemBean {

	@ListColumn(omit = true)
	private Long unumTempleCompId;

	@NotBlank(message = "Print Text is mandatory")
	@ListColumn(name = "Print Text")
	private String ustrCompPrintText;

	private List<TemplateItemBean> items;

}
