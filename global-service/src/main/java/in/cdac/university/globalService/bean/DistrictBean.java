package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Data
@ToString
public class DistrictBean extends GlobalBean {

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
