package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.*;

@Getter
@Setter
public class CommitteeRulesetBean {

    @ComboKey
    @ListColumn(omit = true)
    private Long unumComRsId;

    private Integer unumIsvalid;

    private Date udtEntryDate;

    private Integer unumComRsCatId;

    private Long unumEntryUid;

    private Integer unumNoOfMembers;

    private Integer unumUnivId;

    private String ustrComDescription;

    @ComboValue
    @ListColumn(order = 2, name = "Committe Ruleset Name")
    private String ustrComRsName;

    private Date udtEffFrom;

    private Date udtEffTo;

    private List<CommitteeRulesetDtlBean> committeeRulesetDtl;

    @ListColumn(order = 1, name = "Committe For")
    private String ustrComRsCatName;


    public void setUnumComRsCatId(Integer unumComRsCatId){
        this.unumComRsCatId = unumComRsCatId;
        this.setUstrComRsCatName();
    }

    public void setUstrComRsCatName() {
       this.ustrComRsCatName = (this.getUnumComRsCatId() == 1) ?  "Application Scrutiny" :  "LIC Report Scrutiny" ;
    }
}
