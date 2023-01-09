package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TableBean {

    @ComboKey
    private String tablenameid;
    @ComboValue
    private String tablename;


}
