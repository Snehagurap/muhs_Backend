package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TemplateBean {

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
    private String ustrTempleName;
    private Long unumMtempledtlId;

    private Integer noOfPages;

    private List<TemplateHeaderBean> headers;
}
