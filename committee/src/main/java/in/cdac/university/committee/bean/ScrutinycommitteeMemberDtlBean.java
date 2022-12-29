package in.cdac.university.committee.bean;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ScrutinycommitteeMemberDtlBean {

    private Long unumScomMemberId;

    private Integer unumIsvalid;

    private Date udtEntryDate;

    private Date udtRevalidationDate;

    private Integer unumCtypeid;

    private Long unumEntryUid;

    private Integer unumIsFormulaBased;

    private Long unumLoginId;

    private Long unumScomId;

    private Integer unumScomMemberSno;

    private Long unumScomPref1Empid;

    private String unumScomPref1Empname;

    private Long unumScomPref2Empid;

    private String unumScomPref2Empname;

    private Long unumScomPref3Empid;

    private String unumScomPref3Empname;

    private Long unumScomPref4Empid;

    private String unumScomPref4Empname;

    private Long unumScomPref5Empid;

    private String unumScomPref5Empname;

    private Long unumScomRoleId;

    private Long unumScomRsDtlId;

    private Long unumScomRsId;

    private Integer unumUnivId;

    private String ustrPass;

    private String ustrScomDescription;

    private String unumScomPrefOtherEmpid;

    private String unumVcNominatedFlag;
}
