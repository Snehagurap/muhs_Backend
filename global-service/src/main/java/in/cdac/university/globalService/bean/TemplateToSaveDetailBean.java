package in.cdac.university.globalService.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TemplateToSaveDetailBean {

    private Long unumApplicationId;
    private Long unumApplicationdtlId;
    private Long unumApplicantId;
    private Long unumNid;
    private Long unumNdtlId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private Long unumItemValue;

    @NotNull(message = "Master Template Id is mandatory")
    private Long unumMtempleId;

    @NotNull(message = "Master Template Detail Id is mandatory")
    private Long unumMtempledtlId;

    @NotNull(message = "Component Item Id is mandatory")
    private Long unumTempleCompItemId;

    @NotNull(message = "Component Id  is mandatory")
    private Long unumTempleCompId;

    @NotNull(message = "Head Id is mandatory")
    private Long unumTempleHeadId;

    @NotNull(message = "Template Id is mandatory")
    private Long unumTempleId;

    @NotNull(message = "Item Id is mandatory")
    private Long unumTempleItemId;

    private Long unumTempleSubheadId;

    @NotNull(message = "Template Detail Id is mandatory")
    private Long unumTempledtlId;
    private Integer unumUnivId;
    private String ustrDescription;
    private String ustrItemValue;
    private String ustrTempleItemValue;

    @NotNull(message = "Control Id is mandatory")
    private Integer unumUiControlId;
}
