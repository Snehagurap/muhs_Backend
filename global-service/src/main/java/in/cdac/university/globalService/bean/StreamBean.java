package in.cdac.university.globalService.bean;

 
import java.util.Date;

import javax.validation.constraints.NotNull;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
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
