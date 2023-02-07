package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ScrutinycommitteeBean {

    @ListColumn(omit = true, width = "5%")
    private Long unumScomId;

    private Integer unumIsvalid;

    private Date udtEntryDate;

    private Date udtRevalidationDate;

    private Date udtScomCreateDate;

    @ListColumn(order = 5, name = "From Date", width = "10%")
    private Date udtScomFromDate;

    @ListColumn(order = 6, name = "To Date", width = "10%")
    private Date udtScomToDate;

    private Long unumComRsId;

    @ListColumn(order = 2, name = "Committee Ruleset Name", width = "20%")
    private String ustrComRsName;

    private Integer unumCtypeid;

    private Long unumEntryUid;

    private Integer unumIsFormulaBased;

    private Integer unumLoginRequired;

    private Integer unumNoOfMembers;

    private Long unumScomCfacultyId;

    @ListColumn(order = 3, name = "Faculty Name", width = "15%")
    private String ustrCfacultyName;

    private Long unumStreamId;

    @ListColumn(order = 4, name = "Stream Name", width = "15%")
    private String ustrStreamName;

    private Integer unumUnivId;

    private String ustrComDescription;

    @ListColumn(order = 2, name = "Committee Name", width = "25%")
    private String ustrScomName;

    private Long unumSubId;

    @NotNull(message = "Committee member details are mandatory")
    @Valid
    @Size(min = 1, message = "Committee member details are mandatory")
    private List<ScrutinycommitteeMemberDtlBean> scrutinyComMemberDtl;

    private Integer isSave;

}
