package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class EventTypeBean {

    @ComboKey
    private Long unumEventTypeid;
    private Integer unumIsvalid;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private Integer unumUnivId;
    private String ustrEventTypedesc;

    @ComboValue
    private String ustrEventTypename;
}
