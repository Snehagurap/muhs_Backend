package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ScrutinycommitteeBean {

    @ListColumn(omit = true)
    private Long unumScomId;

    private Integer unumIsvalid;

    private Date udtEntryDate;

    private Date udtRevalidationDate;

    private Date udtScomCreateDate;

    private Date udtScomFromDate;

    private Date udtScomToDate;

    private Long unumComRsId;

    @ListColumn(order = 2,name = "Committee Ruleset Name")
    private String ustrComRsName;

    private Integer unumCtypeid;

    private Long unumEntryUid;

    private Integer unumIsFormulaBased;

    private Integer unumLoginRequired;

    private Integer unumNoOfMembers;

    private Long unumScomCfacultyId;

    @ListColumn(order = 3,name = "Faculty Name")
    private String ustrCfacultyName;

    private Long unumStreamId;

    @ListColumn(order = 4,name = "Stream Name")
    private String ustrStreamName;

    private Integer unumUnivId;

    private String ustrComDescription;

    @ListColumn(order = 2,name = "Committee Name")
    private String ustrScomName;

    private Long unumSubId;

    @NotNull(message = "Committee member details are mandatory")
    @Valid
    @Size(min = 1, message = "Committee member details are mandatory")
    private List<ScrutinycommitteeMemberDtlBean> scrutinyComMemberDtl;

    private Integer isSave;
}
