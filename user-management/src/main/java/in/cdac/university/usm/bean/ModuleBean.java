package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleBean extends GlobalBean {
    @ComboKey
    private Integer gnumModuleId;
    @ComboValue
    private String gstrModuleName;
    private Integer gblIsvalid;
    private Integer gnumModuleType;
    private String gstrModuleSchema;
}
