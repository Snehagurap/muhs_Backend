package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DistrictBean extends GlobalBean {
    @ComboKey
    @ListColumn(omit = true)
    private Integer numDistId;
    @ListColumn(order = 1, name = "District Code", searchable = false)
    private String strDistCode;
    @ComboValue
    @ListColumn(order = 3, name = "District Name")
    private String strDistName;
    @ListColumn(order = 2, name = "Short Name", searchable = false)
    private String strDistStName;
    private Integer gnumSlno;
    private Date gdtEffectiveFrm;
    private Date gdtEffectiveTo;
    private Integer gnumIsvalid;
    private Integer gnumSeatId;
    private Date gdtLstmodDate;
    private Integer gnumLstmodSeatid;
    private String gstrRemarks;
    private Integer gnumStatecode;
    private Integer numZoneId;
    private Double hstnumLongitude;
    private Double hstnumLatitude;
    private String hststrMapFeatureId;
}
