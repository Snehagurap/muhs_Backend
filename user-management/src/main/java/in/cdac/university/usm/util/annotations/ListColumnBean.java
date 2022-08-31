package in.cdac.university.usm.util.annotations;

import lombok.Data;

@Data
public class ListColumnBean {
    private int order;
    private String name;
    private String selector;
    private boolean sortable;
    private boolean omit;
    private boolean searchable;
}
