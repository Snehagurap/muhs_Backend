package in.cdac.university.committee.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeanUtils {

	/**
	 * copies the properties of source object to target object
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 *             - if any one of the parameter object is null
	 */
	public static void copyProperties(Object source, Object target) throws Exception {

		Objects.requireNonNull(source, "Source Object cannot be null");
		Objects.requireNonNull(target, "Target Object cannot be null");

		try {
			org.springframework.beans.BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * copies the parameters of source object to target object by creating new
	 * target object
	 * 
	 * @param source
	 *            - source object
	 * @param targetType
	 *            - target class type
	 * @return - target object with source object bean properties
	 * @throws Exception
	 *             - if source object is null
	 */

	public static <S, T> T copyProperties(S source, Class<T> targetType) {
		Objects.requireNonNull(source, "Target Object (" + targetType.getName() + ") cannot be null");
		return copySourceToTarget(source, targetType);
	}

	/**
	 * copies the bean properties of source objects from source list and
	 * populate it to target list by creating target objects
	 * 
	 * @param sourceList
	 *            - source object list
	 * @param targetList
	 *            - target object list (empty list)
	 * @param targetType
	 *            - target object type
	 * @return target object list with populated properties
	 * @throws Exception
	 */
	public static <S, T> List<T> copyListProperties(List<S> sourceList, Class<T> targetType) {
		return sourceList.stream()
				.map(source -> copySourceToTarget(source, targetType))
				.collect(Collectors.toList());
	}
	
	private static <S, T> T copySourceToTarget(S source, Class<T> targetType) {
		T target = null;
		try {
			target = targetType.getDeclaredConstructor().newInstance();  
			copyProperties(source, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target;
	}

}
