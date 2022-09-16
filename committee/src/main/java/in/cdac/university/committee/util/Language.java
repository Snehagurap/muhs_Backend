package in.cdac.university.committee.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Language {

    @Autowired
    private  ResourceBundleMessageSource messageSource;

    public String message(String message, Object... arguments) {
        try {
            return messageSource.getMessage(message, arguments, RequestUtility.getRequestLocale());
        } catch (NoSuchMessageException noSuchMessageException) {
            return message;
        }
    }

    public String duplicate(String objectName, String duplicateValue) {
        return message("duplicate", objectName, duplicateValue);
    }

    public String mandatory(String objectName) {
        return message("mandatory", objectName);
    }

    public String notFoundForId(String objectName, Object id) {
        return message("not.found.for.id", objectName, id);
    }

    public String saveSuccess(String objectName) {
        return message("save.success", objectName);
    }

    public String saveError(String objectName) {
        return message("save.error", objectName);
    }

    public String updateSuccess(String objectName) {
        return message("update.success", objectName);
    }

    public String updateError(String objectName) {
        return message("update.error", objectName);
    }

    public String deleteSuccess(String objectName) {
        return message("delete.success", objectName);
    }

    public String deleteError(String objectName) {
        return message("delete.error", objectName);
    }
}
