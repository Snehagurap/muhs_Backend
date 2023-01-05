package in.cdac.university.committee.bean;

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

    private Long unumScomId;

    private Integer unumIsvalid;

    private Date udtEntryDate;

    private Date udtRevalidationDate;

    private Date udtScomCreateDate;

    private Date udtScomFromDate;

    private Date udtScomToDate;

    private Long unumComRsId;

    private Integer unumCtypeid;

    private Long unumEntryUid;

    private Integer unumIsFormulaBased;

    private Integer unumLoginRequired;

    private Integer unumNoOfMembers;

    private Integer unumScomCfacultyId;

    private Long unumStreamId;

    private Integer unumUnivId;

    private String ustrComDescription;

    private String ustrScomName;

    private Long unumSubId;

    @NotNull(message = "Committee member details are mandatory")
    @Valid
    @Size(min = 1, message = "Committee member details are mandatory")
    private List<ScrutinycommitteeMemberDtlBean> scrutinyComMemberDtl;
}
