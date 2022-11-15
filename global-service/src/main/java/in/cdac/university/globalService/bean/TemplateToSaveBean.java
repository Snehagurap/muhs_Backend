package in.cdac.university.globalService.bean;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TemplateToSaveBean {

    @NotNull(message = "Master Template Id is mandatory")
    private Long unumMtempleId;

    @NotNull(message = "Notification Id is mandatory")
    private Long unumNid;

    @NotNull(message = "Notification Detail id is mandatory")
    private Long unumNdtlId;

    @NotNull(message = "Application status is mandatory")
    private Integer unumApplicationEntryStatus;

    @NotNull(message = "Item details are mandatory.")
    @Size(min = 1, message = "Item details are mandatory.")
    @Valid
    private List<TemplateToSaveDetailBean> itemDetails;
}
