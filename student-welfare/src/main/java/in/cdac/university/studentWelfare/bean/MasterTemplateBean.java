package in.cdac.university.studentWelfare.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.studentWelfare.util.annotations.ComboKey;
import in.cdac.university.studentWelfare.util.annotations.ComboValue;
import in.cdac.university.studentWelfare.util.annotations.ListColumn;
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
    @ListColumn(omit = true)
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
    @ListColumn(name = "Master Template Name", order = 2)
    private String ustrMtempleName;

    private Integer unumApplicationEntryStatus;

    private Long unumApplicationId;

    private List<TemplateBean> templateList;
    
    private Long unumMtempledtlId;
    
    private Long unumTempleId;
    
    private String ustrMtempdtlDescription;
    
    private Integer unumTemplateOrderNo;
    
    private String ustrMtemplateHeader;
    
    private String ustrMtemplateFooter;
    
    private List<Long> mappedTemplates;
    
    @ListColumn(name = "Template Name", order = 3)
    private String ustrtempleName;

    private String ustrAcademicYear;
    
}
