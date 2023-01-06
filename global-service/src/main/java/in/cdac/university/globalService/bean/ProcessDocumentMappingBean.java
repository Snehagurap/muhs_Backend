package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ProcessDocumentMappingBean {

    private Long unumDocId;

    private Integer unumIsvalid;

    @ListColumn(omit = true)
    private Long unumProcessId;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtLstModDt;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private String ustrDescription;

    private String ustrDocFname;

    private String ustrDocSname;

    @NotNull(message = "Documents to map are mandatory")
    private List<Long> mappedDocuments;

    @ListColumn(name = "Process Name" , order = 2)
    private String processName;

    @ListColumn(name = "Documents Name" , order = 3)
    private String mappedDocumentsName;
}
