package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ApplicantTypeBean {

    @ComboKey
    private Long unumApplicantTypeId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private String ustrApplicantTypeCode;

    private String ustrApplicantTypeFname;

    @ComboValue
    private String ustrApplicantTypeSname;

    private String ustrDescription;
}
