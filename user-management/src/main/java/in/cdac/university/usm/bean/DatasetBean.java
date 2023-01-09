package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DatasetBean extends GlobalBean{
    @ComboKey
    @ListColumn(omit = true)
    private Integer gnumDatasetId;

    @ListColumn(order = 3, name = "Table Name")
    private String gstrTableName;
    @ListColumn(order = 4, name = "Column Name")
    private String gstrColumnName;
    @ListColumn(order = 5, name = "Display Column Name")
    private String gstrDisplayColumn;
    private Integer gblIsvalid;

    @ComboValue
    @ListColumn(order = 2, name = "Dataset Name")
    private String gstrDatasetName;

    @ComboKey(index = 2)
    private Integer gnumModuleId;


    private String gstrSchemaName;
    private Date gdtLstmodDate;
    private Integer gnumLstmodBy;

    private Long gnumEntryBy;

    private Date gdtEntryDate;
    List<DatasetFilterBean> datasetFilterList;

    @ListColumn(order = 6, name = "Filter Column")
    private String gstrFilterColumnName;

    private String gstrModuleId;
}
