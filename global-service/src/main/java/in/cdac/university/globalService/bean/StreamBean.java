package in.cdac.university.globalService.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamBean extends GlobalBean{

	@NotNull(message = "Status is mandatory")
	private Long unumStreamId;

    @NotNull(message = "Status is mandatory")
    private Integer unumIsvalid;
    
    @NotNull(message = "Status is mandatory")
    private String ustrStreamFname;
    
	private Date udtEffFrom;

	private Date udtEffTo;
	

	private Date udtLstModDate;
	
	
	private Date udtEntryDate;

	@NotNull(message = "Status is mandatory")
	private String ustrStreamCode;
	

	private String ustrStreamSname;
	
	private String ustrDescription;
	
	private Integer unumUnivId;
	
	
	private Long unumEntryUid;
	
	private Integer unumLstModUid;
	
	
    
}
