package in.cdac.university.usm.util;

import in.cdac.university.usm.bean.ComboBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class ComboUtility {

    public static List<ComboBean> generateComboData(List<?> dataList) throws IllegalAccessException {
        Objects.requireNonNull(dataList, "List is empty");

        List<ComboBean> comboData = new ArrayList<>();
        for (Object obj : dataList) {
            Class<?> clazz = obj.getClass();
            List<String> key = new ArrayList<>();
            List<String> value = new ArrayList<>();
            for (Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ComboKey.class)) {
                    key.add(String.valueOf(field.get(obj)));
                }
                if (field.isAnnotationPresent(ComboValue.class)) {
                    value.add(String.valueOf(field.get(obj)));
                }
            }
            if (key.isEmpty() || value.isEmpty()) {
                throw new IllegalArgumentException("Objects in the list does not have @ComboKey or @ComboValue annotations");
            }
            String comboKey = Base64.getEncoder().encodeToString(String.join("^", key).getBytes());
            String comboValue = String.join(", ", value);
            comboData.add(new ComboBean(comboKey, comboValue));
        }
        return comboData;
    }
}
