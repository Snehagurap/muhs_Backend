package in.cdac.university.globalService.bean;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TemplateMasterBean {
    @ListColumn(omit = true)
    @ComboKey
    private Long unumTempleId;

    @JsonIgnore
    private Integer unumIsvalid;

    @ListColumn(omit = true)
    @NotNull(message = "Valid From is mandatory")
    private Date udtEffFrom;

    @ListColumn(omit = true)
    private Date udtEffTo;

    @NotNull(message = "Template Date is mandatory")
    private Date udtEntryDate;
    
    @JsonIgnore
    private Long unumEntryUid;

    @NotNull(message = "Faculty is mandatory")
    private Integer unumFacultyId;
    
    private Integer unumTemplateForYyyy;
    
    private Integer unumTemplateForYyyymm;
    
    private Integer unumTemplateType;
    
    @JsonIgnore
    private Integer unumUnivId;
    
    private String ustrTempDescription;
    @ListColumn(name = "Template Code")
    private String ustrTempleCode;
    @ComboValue
    @ListColumn(name = "Template Name")
    @NotBlank(message = "Template Name is mandatory")
    private String ustrTempleName;
    
    @Valid
    private List<TemplateMasterDtlsBean> templateMasterDtlsBeanList;

    private Set<CompHeadSubHeader> compHeadSubHeaders;

    private String ustrCfacultyFname;
    
    private String ustrCfacultySname;

    @NotNull(message = "Course Type is mandatory")
    private Integer unumCtypeId;

    private Integer unumChecklistId;
    private String ustrChecklistName;
}
