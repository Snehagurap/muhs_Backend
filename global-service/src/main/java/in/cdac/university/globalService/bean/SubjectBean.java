package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class SubjectBean extends GlobalBean {

    @ListColumn(omit = true)
    private Long unumSubId;

    @NotNull(message = "Status is mandatory")
    private Integer unumIsvalid;

    @NotNull(message = "Effective From Date is mandatory")
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private Integer unumExonnSubid;
    private Integer unumIntranetsubid;
    private Integer unumSubCategoryId;

    @NotNull(message = "Subject Duration (in Months) is mandatory")
    private Integer unumSubDurationInmm;

    @NotNull(message = "Subject Duration (in Years) is mandatory")
    private Integer unumSubDurationInyy;

    private Integer unumSubtypeId;
    private Integer unumUnivId;

    @Length(max = 300, message = "Description must be 200 characters in length.")
    private String ustrDescription;

    @Length(max = 300, message = "Subject Code must be 300 characters in length.")
    @NotBlank(message = "Subject Code is mandatory")
    @ListColumn(order = 3, name = "Subject Code")
    private String ustrSubCode;

    @Length(max = 300, message = "Subject Name must be 300 characters in length.")
    @NotBlank(message = "Subject Name is mandatory")
    @ListColumn(order = 2, name = "Subject Name")
    private String ustrSubFname;

    @Length(max = 300, message = "Subject Short Name must be 300 characters in length.")
    @NotBlank(message = "Subject Short Name is mandatory")
    private String ustrSubSname;
    private String ustrTeachersSubId;
}
