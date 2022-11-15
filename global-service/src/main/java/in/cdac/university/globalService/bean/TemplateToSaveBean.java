package in.cdac.university.globalService.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TemplateToSaveBean {

    @NotNull(message = "Master Template Id is mandatory")
    private Long unumMtempleId;

    @NotNull(message = "Notification Id is mandatory")
    private Long unumNid;

    @NotNull(message = "Notification Detail id is mandatory")
    private Long unumNdtlId;
}
