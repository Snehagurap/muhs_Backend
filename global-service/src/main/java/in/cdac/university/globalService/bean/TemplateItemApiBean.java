package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TemplateItemApiBean {

    @ListColumn(omit = true)
    private Long unumApiId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Integer unumApiFunctionMode;

    private Integer unumApiTypeId;

    private Long unumEntryUid;

    private String ustrApiFunctionName;

    @ListColumn(name = "Api Name", order = 2)
    private String ustrApiName;

    @ComboKey
    @ComboValue
    @ListColumn(name = "Api Url", order = 3)
    private String ustrApiUrl;

    private String ustrApiUrlParams;

    private String ustrDescription;
}

