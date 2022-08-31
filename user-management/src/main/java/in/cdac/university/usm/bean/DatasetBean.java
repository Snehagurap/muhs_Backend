package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import lombok.Data;

import java.util.Date;

@Data
public class DatasetBean {
    @ComboKey
    private Integer gnumDatasetId;

    private String gstrTableName;
    private String gstrColumnName;
    private String gstrDisplayColumn;
    private Integer gblIsvalid;

    @ComboValue
    private String gstrDatasetName;

    private Integer gnumModuleId;
    private Date gdtLstmodDate;
    private Integer gnumLstmodBy;
}
