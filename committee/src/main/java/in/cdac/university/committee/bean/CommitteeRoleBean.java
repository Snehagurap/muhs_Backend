package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import lombok.Data;

import java.util.Date;

@Data
public class CommitteeRoleBean {

    @ComboKey
    private Integer unumRoleId;
    private Integer unumIsvalid;
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumUnivId;
    private String ustrDescription;

    @ComboValue
    private String ustrRoleFname;
    private String ustrRoleSname;
}
