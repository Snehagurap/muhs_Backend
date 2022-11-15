package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class MenuBean extends GlobalBean {

    @ComboKey
    @ListColumn(omit = true)
    private Integer gnumMenuId;

    private Integer gnumParentId;

    @NotNull(message = "Root Menu is Mandatory")
    private Integer rootMenuId;

    @ComboValue
    //@NotBlank(message = "Menu Name is mandatory")
    @Length(max = 500, message = "Menu Name can contain maximum of 500 characters")
    @ListColumn(order = 2, name = "Menu Name")
    private String gstrMenuName;

    @NotNull(message = "Menu Level is mandatory")
    @ListColumn(order = 3, name = "Menu Level")
    private Integer gnumMenuLevel;

    private Long gnumEntryBy;
    private Date gdtEntryDate;

    @Length(max = 500, message = "Menu URL can contain maximum of 500 characters")
    @ListColumn(order = 5, name = "URL")
    private String gstrUrl;

    private Integer gnumIsvalid;

    @NotNull(message = "Module Name is mandatory")
    private Integer gnumModuleId;

    private String gstrModuleName;

    @ListColumn(order = 4, name = "Parent")
    private String gstrParentName;

    private List<MenuBean> subMenuList;

}
