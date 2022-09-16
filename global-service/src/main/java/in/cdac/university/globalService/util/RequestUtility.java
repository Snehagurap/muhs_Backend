package in.cdac.university.globalService.util;

import com.google.gson.Gson;
import in.cdac.university.globalService.bean.UserDetail;
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

    public static Long getUserId() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String userId = request.getHeader("userId");
        if (userId == null || Long.parseLong(userId) <= 0)
            throw new Exception ("Session not present");
        return Long.parseLong(userId);
    }

    public static UserDetail getUserDetail() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String userDetail = request.getHeader("userDetail");
        if (userDetail == null)
            throw new Exception ("Session not present");
        Gson gson = new Gson();
        return gson.fromJson(userDetail, UserDetail.class);
    }

    public static Integer getUniversityId() throws Exception {
        int universityId = getUserDetail().getUniversityId();
        if (universityId <= 0)
            throw new Exception("Session not present");
        return universityId;
    }
}
