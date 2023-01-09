package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.*;

import java.util.Date;

@Data
public class ProcessBean {

    @ComboKey
    @ListColumn(omit = true)
    private Integer unumProcessId;

    @ListColumn(name = "Effective From", order = 3)
    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtLstModDt;

    private Long unumEntryUid;

    private Integer unumIsvalid;

    private Long unumLstModUid;

    private Integer unumUnivId;

    private String ustrDescription;

    @ListColumn(name = "Process Code" , order = 2)
    private String ustrProcessCode;

    @ComboValue
    @ListColumn(name = "Process Name", order = 1)
    private String ustrProcessName;
}
