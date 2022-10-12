package in.cdac.university.planningBoard.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class NotificationDocumentBean {

    private Long unumNdocid;

    private Integer unumIsvalid;

    private Date udtEntryDate;

    @NotNull(message = "Document Type is mandatory")
    private Integer unumDoctypeId;

    @NotBlank(message = "Document Type Name is mandatory")
    private String unumDoctypeName;

    private Long unumEntryUid;

    private Long unumNid;

    private Integer unumSNo;

    private Integer unumSnoDisplayorder;

    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrFilePath;
}
