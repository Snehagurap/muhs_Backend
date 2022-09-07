package in.cdac.university.committee.bean;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.annotations.IdProperty;
import in.cdac.university.committee.util.annotations.ListColumnBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class GlobalBean implements Serializable {

	private String id;
	private String[] idsToDelete;
	private String strMessage;

	// -1 error message, 0 normal message, 1 warning message
	private int strMessageType = 0;

	public String toString() {
		String str = "";
		try {
			Class<?> clazz = this.getClass();
			Map<String, String> elements = new HashMap<>();
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(IdProperty.class)) {
					elements.put(field.getName(), String.valueOf(field.get(this)));
				}
			}
			
			str = elements.entrySet()
								.stream()
								.map(entry -> "\"" + entry.getKey() 
											+ "\":\"" + entry.getValue() + "\"")
								.collect(Collectors.joining(","));
		} catch (Exception e) {
			e.printStackTrace();
		}
		str = "{" + str + "}";
		return Base64.getEncoder().encodeToString(str.getBytes());
	}
	
	public GlobalBean toObject() {
		return toObject(this.id);
	}
	
	public GlobalBean toObject(String objectAsString) {
		try {
			if (objectAsString == null)
				objectAsString = this.toString();
			Objects.requireNonNull(objectAsString);
			objectAsString = new String(Base64.getDecoder().decode(objectAsString));
			
			Class<?> clazz = this.getClass();
			Type type = new TypeToken<Map<String, String>>(){}.getType();
			Map<String, String> elements = new Gson().fromJson(objectAsString, type);

			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(IdProperty.class)) {
					Object o = toObject(field.getType(), elements.get(field.getName()));
					field.set(this, o);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	private Object toObject(Class<?> clazz, String value) {
		if (value == null || value.equals("null"))
			return null;
		if (Boolean.class == clazz)
			return Boolean.parseBoolean(value);
		if (Byte.class == clazz)
			return Byte.parseByte(value);
		if (Short.class == clazz)
			return Short.parseShort(value);
		if (Integer.class == clazz)
			return Integer.parseInt(value);
		if (Long.class == clazz)
			return Long.parseLong(value);
		if (Float.class == clazz)
			return Float.parseFloat(value);
		if (Double.class == clazz)
			return Double.parseDouble(value);
		return value;
	}

	public List<ListColumnBean> generateListPageColumns() throws IllegalAccessException {
		return ListPageUtility.getColumns(this.getClass());
	}
}
