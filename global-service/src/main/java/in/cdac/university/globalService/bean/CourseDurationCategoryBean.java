package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class CourseDurationCategoryBean {

    @ComboKey
    private Long unumCdCategoryId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private String unumExonCdCategoryid;
    private String unumIntraCdCategoryid;
    private Integer unumUnivId;

    @ComboValue
    private String ustrCdCategoryFname;
    private String ustrCdCategorySname;
    private String ustrDescription;
}
