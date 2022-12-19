package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Long unumDocId;

    @JsonIgnore
    private Integer unumIsvalid;

    private Integer isMandatory = 0;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    @JsonIgnore
    private Long unumEntryUid;

    @JsonIgnore
    private String ustrDescription;

    private String ustrDocName;

    @JsonIgnore
    private String ustrDocCode;
}

