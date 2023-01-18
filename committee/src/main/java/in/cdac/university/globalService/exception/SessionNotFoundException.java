package in.cdac.university.globalService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SessionNotFoundException extends RuntimeException {

    private String message;
}
