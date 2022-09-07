package in.cdac.university.planningBoard.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

public class RequestUtility {

    public static Locale getRequestLocale() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String language = request.getHeader("Accept-Language");
        if (language == null)
            language = "en";
        return new Locale(language);
    }

    public static Integer getUserId() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String userId = request.getHeader("userId");
        if (userId == null)
            throw new Exception ("Session not present");
        return Integer.valueOf(userId);
    }

    public static Integer getApplicationType() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String applicationType = request.getHeader("applicationType");
        if (applicationType == null)
            throw new Exception ("Session not present");
        return Integer.valueOf(applicationType);
    }
}
