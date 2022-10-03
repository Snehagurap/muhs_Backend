package in.cdac.university.globalService.util;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestResponse {

    private Integer status;
    private String message;
    private HashMap<String, String> data;
}
