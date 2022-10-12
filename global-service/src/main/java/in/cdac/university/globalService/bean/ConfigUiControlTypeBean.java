package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ConfigUiControlTypeBean {
    @ComboKey
    private Long unumUictId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrUictCode;

    @ComboValue
    private String ustrUictDesc;
}
