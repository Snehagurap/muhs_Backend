package in.cdac.university.globalService.bean;


import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
public class DropdownBean {

    private Long unumDropdownId;

    @ComboKey
    private Long unumDropdownItemId;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Integer unumIsvalid;

    private Integer unumUnivId;

    private String ustrDescription;

    @ComboValue
    private String ustrDropdownItemName;

    private Double unumOrderNo;
}
