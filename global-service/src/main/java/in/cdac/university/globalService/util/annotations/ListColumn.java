package in.cdac.university.globalService.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ListColumn {
    int order() default 1;

    String name() default "";

    boolean sortable() default true;

    boolean omit() default false;

    boolean searchable() default true;

    String width() default "";
}
