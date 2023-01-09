package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
public class FacultyStreamDtlBean {

    @ComboKey
    private Long unumFacStreamId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtLstModDate;

    @NotNull(message = "Faculty is mandatory")
    @ListColumn(omit = true)
    private Integer unumCfacultyId;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private Long unumStreamId;

    private Integer unumUnivId;

    private String ustrDescription;

    @ComboValue(order = 1)
    private String ustrFacStreamCode;

    @ComboValue(order = 2)
    @ListColumn(order = 3, name=" Stream Names", width="80%")
    private String ustrFacStreamFname;

    private String ustrFacStreamSname;

    private List<Long> mappedStreamIds;

    @ListColumn(order = 2, name="Faculty Name", width="20%")
    private String gstrFacultyName;
}
