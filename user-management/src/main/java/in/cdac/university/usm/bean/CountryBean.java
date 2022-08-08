package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.ComboKey;
import in.cdac.university.usm.util.ComboValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryBean extends GlobalBean {
    @ComboKey
    private Integer gnumCountrycode;
    @ComboValue
    private String gstrCountryname;
    private String gstrCountryshort;
    private Integer gnumSeatid;
    private Integer gnumIsvalid;
    private String gstrNationality;
    private Integer gnumIsDefaultCountry;
    private Long gnumHl7Code;
}
