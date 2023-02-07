package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StateBean extends GlobalBean {
    @ComboKey
    @ListColumn(omit = true)
    private Integer gnumStatecode;

    private Integer gnumCountrycode;

    @ComboValue
    @ListColumn(order = 2, name = "State Name")
    private String gstrStatename;

    @ListColumn(order = 3, name = "State Shortname")
    private String gstrStateshort;

    private Integer gnumSeatid;
    private Integer gnumIsvalid;
    private Date gdtLstmodDate;
    private Integer gnumLstmodSeatid;
    private String gstrRemarks;
    private Integer gnumHl7Code;
    private Integer gnumIsDefaultState;
    private Integer gnumIsDefaultUt;
    private String gstrDatasourceName;
    private String gstrSchemaName;
}
