package in.cdac.university.studentWelfare.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataNotFoundForGivenEnrollmentNumber extends Exception {

	 private String message;
}
