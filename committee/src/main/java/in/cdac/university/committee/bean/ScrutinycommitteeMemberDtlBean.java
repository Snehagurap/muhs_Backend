package in.cdac.university.committee.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
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

    private String ustrScomPref1Empname;

    private Long unumScomPref2Empid;

    private String ustrScomPref2Empname;

    private Long unumScomPref3Empid;

    private String ustrScomPref3Empname;

    private Long unumScomPref4Empid;

    private String ustrScomPref4Empname;

    private Long unumScomPref5Empid;

    private String ustrScomPref5Empname;

    private Integer unumRoleId;
    private String ustrRoleName;

    private Long unumComRsDtlId;

    private Long unumComRsId;

    private Integer unumUnivId;

    private String ustrPass;

    private String ustrScomDescription;

    private Long unumScomPrefOtherEmpid;
    private String ustrScomPrefOtherEmpname;

    private Integer unumVcNominatedFlag;
}
