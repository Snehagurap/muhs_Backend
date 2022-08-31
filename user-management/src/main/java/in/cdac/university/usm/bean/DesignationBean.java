package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DesignationBean {
    @ComboKey
    private Integer gnumDesigCode;
    @ComboValue
    private String gstrDesignationName;
    private String gstrDesignationShortName;
    private Integer gnumSeatid;
    private Integer gnumIsvalid;
    private String gstrRemarks;
}
