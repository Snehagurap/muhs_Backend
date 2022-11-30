package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class MasterTemplateBean {

    @ComboKey
    private Long unumMtempleId;

    @JsonIgnore
    private Integer unumIsvalid;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    private Integer unumCtypeId;

    @JsonIgnore
    private Long unumEntryUid;

    private Integer unumFacultyId;

    private Integer unumMtemplateForYyyy;

    private Integer unumMtemplateForYyyymm;

    private Integer unumMtemplateType;

    @JsonIgnore
    private Integer unumUnivId;

    private String ustrMtempDescription;

    private String ustrMtempleCode;

    @ComboValue
    private String ustrMtempleName;

    private Integer unumApplicationEntryStatus;

    private Long unumApplicationId;

    private List<TemplateBean> templateList;
}
