package in.cdac.university.committee.bean;


import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@ToString
public class StreamBean {
	
    @ComboKey
    @ListColumn(omit = true)
    private Long unumStreamId;
    
    @NotNull(message = "Status is mandatory")
    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtLstModDate;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private Integer unumUnivId;

    private String ustrDescription;
    
    @NotNull(message = "Stream Code is mandatory")
    @ListColumn(name = "Stream Code")
    private String ustrStreamCode;
    
    
    @NotNull(message = "Stream Name is mandatory")
    @ComboValue
    @ListColumn(name = "Stream Name", order = 2)
    private String ustrStreamFname;

    private String ustrStreamSname;

}
