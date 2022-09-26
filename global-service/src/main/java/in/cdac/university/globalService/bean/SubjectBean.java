package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubjectBean {

    @ListColumn(omit = true)
    private Long unumSubId;

    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private Integer unumExonnSubid;
    private Integer unumIntranetsubid;
    private Integer unumSubCategoryId;
    private Integer unumSubDurationInmm;
    private Integer unumSubDurationInyy;
    private Integer unumSubtypeId;
    private Integer unumUnivId;
    private String ustrDescription;

    @ListColumn(order = 3, name = "Subject Code")
    private String ustrSubCode;

    @ListColumn(order = 2, name = "Subject Name")
    private String ustrSubFname;
    private String ustrSubSname;
    private String ustrTeachersSubId;
}
