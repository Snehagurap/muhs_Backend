package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Data;

import java.util.Date;

@Data
public class CommitteeRoleBean {

    @ComboKey
    @ListColumn(omit = true)
    private Integer unumRoleId;
    private Integer unumIsvalid;

    @ListColumn(name = "Effective From", order = 3)
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumUnivId;

    @ListColumn(name = "Remarks", order = 4)
    private String ustrDescription;

    @ListColumn(name = "Role Full Name", order = 1)
    @ComboValue
    private String ustrRoleFname;

    @ListColumn(name = "Role Short Name", order = 2)
    private String ustrRoleSname;
}
