package in.cdac.university.studentWelfare.bean;

import in.cdac.university.studentWelfare.util.annotations.ComboKey;
import in.cdac.university.studentWelfare.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ApplicationStatusBean {


    @ComboKey
    private Integer unumApplicationStatusId;


    private Integer unumIsvalid;

    @ComboValue
    private String ustrApplicationStatusName;

    private Date udtEntryDate;


}
