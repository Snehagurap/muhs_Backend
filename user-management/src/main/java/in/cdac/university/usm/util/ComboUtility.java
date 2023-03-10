package in.cdac.university.usm.util;

import in.cdac.university.usm.bean.ComboBean;
import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;

import java.lang.reflect.Field;
import java.util.*;

public class ComboUtility {

    public static List<ComboBean> generateComboData(List<?> dataList) throws IllegalAccessException {
        Objects.requireNonNull(dataList, "List is empty");

        List<ComboBean> comboData = new ArrayList<>();
        for (Object obj : dataList) {
            Class<?> clazz = obj.getClass();
            Map<Integer, String> key = new TreeMap<>();
            List<String> value = new ArrayList<>();
            for (Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ComboKey.class)) {
                    ComboKey comboKey = field.getAnnotation(ComboKey.class);
                    key.put(comboKey.index(), String.valueOf(field.get(obj)));
                }
                if (field.isAnnotationPresent(ComboValue.class)) {
                    ComboValue comboValue = field.getAnnotation(ComboValue.class);
                    value.add(comboValue.startSeparator() + field.get(obj) + comboValue.endSeparator());
                }
            }
            if (key.isEmpty() || value.isEmpty()) {
                throw new IllegalArgumentException("Objects in the list does not have @ComboKey or @ComboValue annotations");
            }
            String comboKey = String.join("^", key.values());
            String comboValue = String.join(" ", value);
            comboData.add(new ComboBean(comboKey, comboValue));
        }
        return comboData;
    }
}
