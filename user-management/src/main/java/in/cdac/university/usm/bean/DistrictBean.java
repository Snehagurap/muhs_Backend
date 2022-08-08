package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.ComboKey;
import in.cdac.university.usm.util.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DistrictBean {
    @ComboKey
    private Integer numDistId;
    private String strDistCode;
    @ComboValue
    private String strDistName;
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
