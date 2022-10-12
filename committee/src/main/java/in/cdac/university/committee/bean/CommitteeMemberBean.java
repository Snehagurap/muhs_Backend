package in.cdac.university.committee.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CommitteeMemberBean {
    @NotNull(message = "Committee is mandatory")
    private Long unumComId;

    @NotNull(message = "Event is mandatory")
    private Long unumEventid;
    private Integer unumUnivId;
    private Integer unumIsvalid;
    private Long unumEntryUid;
    private Date udtEntryDate;

    @NotNull(message = "Committee member details are mandatory")
    @Valid
    @Size(min = 1, message = "Committee member details are mandatory")
    List<CommitteeMember> members;
}

