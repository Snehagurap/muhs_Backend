package in.cdac.university.usm.util;

import com.google.gson.Gson;
import in.cdac.university.usm.bean.UserDetail;
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

    public static UserDetail getUserDetail() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String userDetail = request.getHeader("userDetail");
        if (userDetail == null)
            throw new Exception ("Session not present");
        Gson gson = new Gson();
        return gson.fromJson(userDetail, UserDetail.class);
    }

    public static Integer getUserCategory() throws Exception {
        int userCategory = getUserDetail().getUserCategory();
        if (userCategory <= 0)
            throw new Exception("Session not present");
        return userCategory;
    }
}
