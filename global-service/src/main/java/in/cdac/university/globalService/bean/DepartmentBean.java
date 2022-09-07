package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Data;

import java.util.Date;

@Data
public class DepartmentBean extends GlobalBean {

    @ComboKey
    @ListColumn(omit = true)
    private Integer unumDeptId;

    private Integer unumIsvalid;
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumParentDeptId;
    private Integer unumUnivId;

    @ComboValue
    @ListColumn(order = 2, name = "Department Name")
    private String ustrDeptFname;

    private String ustrDeptSname;
    private String ustrDescription;

    @ListColumn(order = 3, name = "Parent Department Name")
    private String parentDepartmentName;
}
