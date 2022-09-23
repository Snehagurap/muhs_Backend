package in.cdac.university.committee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SessionNotFoundException extends RuntimeException {

    private String message;
}
