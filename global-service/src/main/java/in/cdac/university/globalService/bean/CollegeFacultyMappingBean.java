package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CollegeFacultyMappingBean {

    private Long unumColFacId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;

    @NotNull(message = "Collge Name is mandatory")
    private Long unumCollegeId;
    private Long unumEntryUid;

    @NotNull(message = "Faculties to map are mandatory")
    private List<Integer> mappedFaculties;

    private Integer unumFacultyId;
    private Integer unumUnivId;
    private String ustrDescription;
}
