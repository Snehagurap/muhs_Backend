package in.cdac.university.usm.bean;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DatasetFilterBean {

    private String gstrTableName;

    private String gstrFilterColumnName;

    private String gstrFilterQuery;

    private Integer gblIsvalid;

    private Date gdtEntryDate;

    private Integer gnumModuleId;

    private Integer gstrFilterOrder;

    private String gstrFilterDisplay;

    private Integer gnumDatasetId;
}
