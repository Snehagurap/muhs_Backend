package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserDatasetBean extends  GlobalBean{

    private Integer gnumDatasetId;

    private Integer unumUnivId;

    @NotNull(message = "User is mandatory")
    private Long gnumUserId;

    private Date gdtEntryDate;

    private Integer gnumDefaultDatasetId;

    @ComboKey
    private Integer gstrColumnValue;

    private Integer gnumModuleId;

    private Integer gnumIsDefault;
    private Integer gnumIsvalid;

    private String[] mappedDatasets;

    @ComboValue
    @ComboKey(index = 2)
    private String gstrDisplayValue;

}
