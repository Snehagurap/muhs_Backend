package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class ApplicantDetailBean {
    private Long unumApplicantDocId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumApplicantId;
    @NotNull(message = "Document id is mandatory")
    private Integer unumDocId;
    private Integer unumDocIsVerified;
    private Long unumEntryUid;
    private String ustrDescription;
    private String ustrDocName;
    private String ustrDocPath;
}
