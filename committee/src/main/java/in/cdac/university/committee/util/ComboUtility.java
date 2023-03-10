package in.cdac.university.committee.util;

import in.cdac.university.committee.bean.ComboBean;
import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;

import java.lang.reflect.Field;
import java.util.*;

public class ComboUtility {

    public static List<ComboBean> generateComboDataWithSelectValue(List<?> dataList) throws IllegalAccessException {
        List<ComboBean> comboData = new ArrayList<>();
        comboData.add(new ComboBean("", "Select Value"));
        comboData.addAll(generateComboData(dataList));
        return comboData;
    }

    public static List<ComboBean> generateComboDataWithAll(List<?> dataList) throws IllegalAccessException {
        List<ComboBean> comboData = new ArrayList<>();
        comboData.add(new ComboBean("0", "All"));
        comboData.addAll(generateComboData(dataList));
        return comboData;
    }

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
                    value.add(String.valueOf(field.get(obj)));
                }
            }
            if (key.isEmpty() || value.isEmpty()) {
                throw new IllegalArgumentException("Objects in the list does not have @ComboKey or @ComboValue annotations");
            }
            String comboKey = String.join("^", key.values());
            String comboValue = String.join(", ", value);
            comboData.add(new ComboBean(comboKey, comboValue));
        }
        return comboData;
    }
}
