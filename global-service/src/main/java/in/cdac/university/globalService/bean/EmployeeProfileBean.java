package in.cdac.university.globalService.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString

public class EmployeeProfileBean {
    private Long unumProfileId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumCollegeId;
    private Long unumEntryUid;
    private Integer unumFacultyId;
    private Long unumCourseId;
    private Long unumSubId;
    private Long unumEmpId;
    private Integer unumUnivId;
    private String ustrDescription;

    private Long unumStreamId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeProfileBean that = (EmployeeProfileBean) o;
        return Objects.equals(unumProfileId, that.unumProfileId) && Objects.equals(unumIsvalid, that.unumIsvalid) && Objects.equals(unumCollegeId, that.unumCollegeId) && Objects.equals(unumEntryUid, that.unumEntryUid) && Objects.equals(unumFacultyId, that.unumFacultyId) && Objects.equals(unumCourseId, that.unumCourseId) && Objects.equals(unumSubId, that.unumSubId) && Objects.equals(unumEmpId, that.unumEmpId) && Objects.equals(unumUnivId, that.unumUnivId) && Objects.equals(ustrDescription, that.ustrDescription) && Objects.equals(unumStreamId, that.unumStreamId);
    }

}
