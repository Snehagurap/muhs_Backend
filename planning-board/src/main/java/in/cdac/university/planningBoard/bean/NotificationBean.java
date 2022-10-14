package in.cdac.university.planningBoard.bean;

import in.cdac.university.planningBoard.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class NotificationBean {
    @ListColumn(omit = true)
    private Long unumNid;
    private Integer unumIsvalid;
    private Date udtEntryDate;

    @ListColumn(name = "Notification Date")
    @NotNull(message = "Notification Date is mandatory")
    private Date udtNDt;

    @NotNull(message = "Out Date is mandatory")
    private Date udtNOutDt;

    @NotNull(message = "Valid From is mandatory")
    private Date udtValidFrm;

    @NotNull(message = "Valid To is mandatory")
    private Date udtValidTo;

    private Long unumApprovingUserid;

    @NotNull(message = "For Faculty is mandatory")
    private Integer unumCfacultyId;

    @NotNull(message = "For Department is mandatory")
    private Integer unumDeptId;

    private Long unumEntryUid;

    private Integer unumIsAmmendment = 0;

    @NotNull(message = "Notification Main Language is mandatory")
    private Integer unumMainLangId;
    private Long unumMainNid;

    @NotNull(message = "Notification For is mandatory")
    private Integer unumNRecepient;

    @NotNull(message = "Notificatoin Style is mandatory")
    private Integer unumNStyle;

    @NotNull(message = "Notification Type is mandatory")
    private Integer unumNtypeId;

    private Integer unumUnivId;

    @Pattern(regexp = "[0-9]{4}", message = "Invalid Academic Year")
    @NotNull(message = "Academic Year is mandatory")
    private String ustrAcademicYear;

    @NotNull(message = "Approving Order no. is mandatory")
    private String ustrApprovingOrderno;

    @NotNull(message = "Approved By is mandatory")
    private String ustrApprovingUsername;
    private String ustrDescription;

    @ListColumn(order = 4, name = "Notification Title")
    @NotNull(message = "Notification Title is mandatory")
    private String ustrNMainHeading;

    @ListColumn(order = 2, name = "Notification No.")
    @NotNull(message = "Notification No. is mandatory")
    private String ustrNNo;

    @NotNull(message = "Out Number is mandatory")
    private String ustrNOutNo;

    @NotNull(message = "Notification Sub Title is mandatory")
    private String ustrNSubHeading;

    private List<NotificationDetailBean> notificationDetails;

    @Valid
    @Size(min = 1, message = "Document details are mandatory")
    @NotNull(message = "Document details are mandatory")
    private List<NotificationDocumentBean> documents;

    @ListColumn(order = 3, name = "Notification Type")
    private String notificationTypeName;
}
