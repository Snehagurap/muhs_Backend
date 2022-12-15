package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TemplateMasterBean {
    @ListColumn(omit = true)
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
    
    private List<TemplateMasterDtlsBean> templateMasterDtlsBeanList;
}
