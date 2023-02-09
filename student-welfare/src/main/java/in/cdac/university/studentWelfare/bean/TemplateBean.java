package in.cdac.university.studentWelfare.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.studentWelfare.util.annotations.ComboKey;
import in.cdac.university.studentWelfare.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TemplateBean {
	@ComboKey
    private Long unumTempleId;

    @JsonIgnore
    private Integer unumIsvalid;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    @JsonIgnore
    private Long unumEntryUid;

    private Integer unumFacultyId;
    private Integer unumTemplateForYyyy;
    private Integer unumTemplateForYyyymm;
    private Integer unumTemplateType;

    @JsonIgnore
    private Integer unumUnivId;
    private String ustrTempDescription;
    private String ustrTempleCode;
    @ComboValue
    private String ustrTempleName;
    private Long unumMtempledtlId;

    private Integer noOfPages;

    private List<TemplateHeaderBean> headers;

    private CheckListBean checkList;

    private Map<Long, TemplateItemBean> items;
}
