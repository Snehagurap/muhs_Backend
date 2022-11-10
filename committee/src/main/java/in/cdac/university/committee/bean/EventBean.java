package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class EventBean {

    @ListColumn(omit = true)
    @ComboKey
    private Long unumEventid;
    private Integer unumIsvalid;
    private Date udtEntryDate;

    @ListColumn(order = 4, name = "From Date")
    @NotNull(message = "Event From Date is mandatory")
    private Date udtEventFromdt;

    @ListColumn(order = 5, name = "To Date")
    @NotNull(message = "Event To Date is mandatory")
    private Date udtEventTodt;

    @NotNull(message = "College Name is mandatory")
    private Long unumCollegeId;

    @NotNull(message = "Committee Name is mandatory")
    @Min(value = 1, message = "Committee Name is mandatory")
    private Long unumComid;
    private Long unumEntryUid;

    @NotNull(message = "Event Days is mandatory")
    private Integer unumEventDurationDays;

    @NotNull(message = "Event Type is mandatory")
    private Integer unumEventTypeid;

    private Integer unumUnivId;
    private String ustrCollegeName;
    private String ustrEventDescription;

    @ListColumn(order = 3, name = "Event name")
    @ComboValue
    @NotBlank(message = "Event Name is mandatory")
    private String ustrEventName;

    @ListColumn(order = 2, name = "Event Type")
    private String eventTypeName;

    @ComboKey(index = 2)
    private int isEventStarted;

    @ListColumn(omit = true)
    private Integer unumMemberMapped;

    public void setUnumMemberMapped(Integer unumMemberMapped) {
        this.unumMemberMapped = unumMemberMapped;
        this.setUstrMemberMappedStatus();
    }

    @ListColumn(order = 6, name = "Member mapped")
    private String ustrMemberMappedStatus;

    public void setUstrMemberMappedStatus() {
        if(unumMemberMapped > 0) {
            this.ustrMemberMappedStatus = "Yes";
        } else {
            this.ustrMemberMappedStatus = "No";
        }
    }

    private List<CommitteeMember> committeeMemberDetail;
}
