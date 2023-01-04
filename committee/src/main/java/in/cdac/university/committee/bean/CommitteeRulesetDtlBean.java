package in.cdac.university.committee.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommitteeRulesetDtlBean {

    private Long unumComRsDtlId;

    private Integer unumIsvalid;

    private Date udtEntryDate;

    private Long unumComRsId;

    private Integer unumComrolsno;

    private Long unumEntryUid;

    private Integer unumRoleCfacultyId;

    private Integer unumRoleDepartmentId;

    private Integer unumRoleId;

    private Integer unumRolePostId;

    private Integer unumUnivId;

    private Integer unumVcNominatedFlag;

    private String ustrComroleDescription;

    private Date udtEffFrom;

    private Date udtEffTo;

    private List<ComboBean> teacherCombo;
}
