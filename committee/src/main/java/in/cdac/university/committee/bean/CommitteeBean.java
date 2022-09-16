package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommitteeBean {

    @ComboKey
    private Long unumComid;

    private Integer unumIsvalid;

    @NotNull(message = "Committee Type is mandatory")
    private Integer unumComtypeId;

    @NotNull(message = "Duration (in Days) is mandatory")
    private Integer unumComdurationDays;

    @NotNull(message = "Start Date is mandatory")
    @DateTimeFormat
    private Date udtComStartDate;

    @ComboValue
    @NotBlank(message = "Committee Name is mandatory")
    private String ustrComName;

    @NotNull(message = "Number of Members is mandatory")
    private Integer unumNoOfMembers;

    @NotNull(message = "For Faculty is mandatory")
    private Integer unumComCfacultyId;

    @NotNull(message = "For Year is mandatory")
    private Integer ustrComForyear;


    private Date udtComEndDate;
    private Date udtEntryDate;
    private Integer unumUnivId;
    private String ustrComDescription;
    private Integer ustrComFormonth;
    private Long unumEntryUid;

    @NotNull(message = "Committee details are mandatory")
    @Valid
    @Size(min = 1, message = "Committee details are mandatory")
    private List<CommitteeDetailBean> committeeDetail;
}
