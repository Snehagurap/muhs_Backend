package in.cdac.university.globalService.util;

import in.cdac.university.globalService.util.annotations.ListColumn;
import in.cdac.university.globalService.util.annotations.ListColumnBean;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
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
                listColumnBean.setWidth(column.width());
                columnList.add(listColumnBean);
            }
        }
        columnList.sort(Comparator.comparingInt(ListColumnBean::getOrder));
        return columnList;
    }

    public static List<Map<String, String>> generateListPageData(List<?> dataList) throws IllegalAccessException {
        Objects.requireNonNull(dataList, "List is empty");

        List<Map<String, String>> list = new ArrayList<>();
        SimpleDateFormat sdf = null;
        for (Object obj : dataList) {
            Class<?> clazz = obj.getClass();
            Map<String, String> map = new LinkedHashMap<>();
            for (Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ListColumn.class)) {
                    Object fieldValue = field.get(obj);
                    if (field.getType() == Date.class) {
                        if (sdf == null)
                            sdf = new SimpleDateFormat(Constants.dateFormat);
                        map.put(field.getName(), sdf.format(fieldValue));
                    } else {
                        map.put(field.getName(), String.valueOf(fieldValue == null ? "" : fieldValue));
                    }
                }
            }
            list.add(map);
        }
        return list;
    }
}
