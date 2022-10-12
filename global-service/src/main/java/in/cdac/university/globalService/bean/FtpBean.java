package in.cdac.university.globalService.bean;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FtpBean {

    @NotEmpty(message = "Filename is mandatory")
    private String fileName;
}
