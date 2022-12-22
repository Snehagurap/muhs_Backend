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

@Getter
@Setter
public class TemplateMasterBean {
    @ListColumn(omit = true)
    @ComboKey
    private Long unumTempleId;

    @JsonIgnore
    
    private Integer unumIsvalid;

//    @JsonIgnore
    @ListColumn(omit = true)
    private Date udtEffFrom;

//    @JsonIgnore
    @ListColumn(omit = true)
    private Date udtEffTo;

//    @JsonIgnore
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
    @ListColumn
    private String ustrTempleCode;
    @ComboValue
    @ListColumn
    private String ustrTempleName;
    
    private List<TemplateMasterDtlsBean> templateMasterDtlsBeanList;

    private Set<CompHeadSubHeader> compHeadSubHeaders;

    private String ustrCfacultyFname;
    
    private String ustrCfacultySname; 
    private Integer unumCtypeId;
    
}
