package in.cdac.university.committee.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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

    private Integer unumStreamId;

    private Integer unumUnivId;

    private String ustrComDescription;

    private String ustrScomName;
}
