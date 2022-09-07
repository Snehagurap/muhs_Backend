package in.cdac.university.committee.util;

import in.cdac.university.committee.util.annotations.ListColumn;
import in.cdac.university.committee.util.annotations.ListColumnBean;

import java.lang.reflect.Field;
import java.util.*;

public class ListPageUtility {

    public static List<ListColumnBean> getColumns(Class<?> beanClass) {
        List<ListColumnBean> columnList = new ArrayList<>();
        for (Field field: beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ListColumn.class)) {
                ListColumnBean listColumnBean = new ListColumnBean();
                ListColumn column = field.getAnnotation(ListColumn.class);
                String name = column.name();
                if (name.isEmpty())
                    name = field.getName();
                listColumnBean.setName(name);
                listColumnBean.setSelector("row => row." + field.getName());
                listColumnBean.setSortable(column.sortable());
                listColumnBean.setOrder(column.order());
                listColumnBean.setOmit(column.omit());
                listColumnBean.setSearchable(column.searchable());
                columnList.add(listColumnBean);
            }
        }
        columnList.sort(Comparator.comparingInt(ListColumnBean::getOrder));
        return columnList;
    }

    public static List<Map<String, String>> generateListPageData(List<?> dataList) throws IllegalAccessException {
        Objects.requireNonNull(dataList, "List is empty");

        List<Map<String, String>> list = new ArrayList<>();
        for (Object obj : dataList) {
            Class<?> clazz = obj.getClass();
            Map<String, String> map = new LinkedHashMap<>();
            for (Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ListColumn.class)) {
                    Object fieldValue = field.get(obj);
                    map.put(field.getName(), String.valueOf(fieldValue == null ? "" : fieldValue));
                }
            }
            list.add(map);
        }
        return list;
    }
}
