package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class MasterTemplateBean {

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

    private String ustrMtempleName;
}
