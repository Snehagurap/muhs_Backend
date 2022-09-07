package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CommitteeTypeBean extends GlobalBean {

    @ComboKey
    private Integer unumComtypeId;

    private Integer unumIsvalid;
    private Date udtComtypeValidFrm;
    private Date udtComtypeValidTo;
    private Date udtEntryDate;
    private Integer unumUnivId;
    private String ustrComDescription;

    @ComboValue
    private String ustrComtypeFname;

    private String ustrComtypeSname;
}
