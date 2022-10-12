package in.cdac.university.committee.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CommitteeMember {
    private Long unumComMemberId;

    private Integer unumMemberSno;

    @NotNull(message = "Member Role is mandatory")
    private Integer unumRoleId;

    @NotNull(message = "Committee Role Id is mandatory")
    private Long unumComroleid;

    private Long unumPreference1Empid;
    private String unumPreference1Empname;
    private Long unumPreference2Empid;
    private String unumPreference2Empname;
    private Long unumPreference3Empid;
    private String unumPreference3Empname;
    private Long unumPreference4Empid;
    private String unumPreference4Empname;
    private Long unumPreference5Empid;
    private String unumPreference5Empname;

    private String ustrComMemberDescription;
}
