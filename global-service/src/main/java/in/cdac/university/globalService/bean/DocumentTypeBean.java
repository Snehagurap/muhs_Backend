package in.cdac.university.globalService.bean;


import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DocumentTypeBean {

    private Long key;

    public Long getKey() {
        return unumDocId;
    }

    @ListColumn(omit = true)
    private Long unumDocId;


    private Integer unumIsvalid;

    private Integer isMandatory = 0;


    private Date udtEffFrom;


    private Date udtEffTo;


    private Date udtEntryDate;


    private Long unumEntryUid;


    private Date udtLstModDt;


    private Long unumLstModUid;


    @ListColumn(name = "Remarks", order = 3)
    private String ustrDescription;

    @ListColumn(name = "Document Name", order = 2)
    private String ustrDocName;


    @ListColumn(name = "Document Code", order = 1)
    private String ustrDocCode;
}

