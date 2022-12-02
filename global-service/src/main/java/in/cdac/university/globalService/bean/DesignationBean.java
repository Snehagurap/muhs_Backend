package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;

@Getter
@Setter
@ToString
public class DesignationBean {
    @ComboKey
    @ListColumn(omit = true)
    private Integer unumPostId;
    @ListColumn(name = "Status", order = 4)
    private Integer unumIsvalid;
    @ListColumn(name = "Effective From", order = 3)
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumParentPostId;
    private Integer unumUnivId;
    private String ustrDescription;

    @ComboValue
    @ListColumn(name = "Post Full Name", order = 1)
    private String ustrPostFname;
    @ListColumn(name = "Post Short Name", order = 2)
    private String ustrPostSname;
}
