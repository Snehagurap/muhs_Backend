package in.cdac.university.committee.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class EventBean {

    private Long unumEventid;
    private Integer unumIsvalid;
    private Date udtEntryDate;
    private Date udtEventFromdt;
    private Date udtEventTodt;
    private Integer unumCollegeId;
    private Long unumComid;
    private Long unumEntryUid;
    private Integer unumEventDurationDays;
    private Integer unumEventTypeid;
    private Integer unumUnivId;
    private String ustrCollegeName;
    private String ustrEventDescription;
    private String ustrEventName;
}
