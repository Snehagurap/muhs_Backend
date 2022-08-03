package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.ComboKey;
import in.cdac.university.usm.util.ComboValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCategoryBean {
    @ComboKey
    private Integer gnumUserCatId;
    @ComboValue
    private String gstrUserCatName;
    private Integer gnumIsvalid;
}
