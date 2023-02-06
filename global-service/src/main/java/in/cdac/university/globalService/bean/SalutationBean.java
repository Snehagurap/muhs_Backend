package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class SalutationBean {

    @ComboKey
    private Integer unumSalutationId;

    private Integer unumIsvalid;

    private String ustrSalutationSname;

    @ComboValue
    private String ustrSalutationFname;

    private Date udtEffFrom;

    private Date udtEffTo;

    private String ustrDescription;

    private Integer unumUnivId;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private Date udtLstModDt;
}
