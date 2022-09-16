package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@ToString
public class DesignationBean {
    @ComboKey
    private Integer unumPostId;
    private Integer unumIsvalid;
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumParentPostId;
    private Integer unumUnivId;
    private String ustrDescription;

    @ComboValue
    private String ustrPostFname;
    private String ustrPostSname;
}
