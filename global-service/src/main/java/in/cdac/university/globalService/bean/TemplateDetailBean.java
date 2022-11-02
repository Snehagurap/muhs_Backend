package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TemplateDetailBean {

    private Long unumTempledtlId;

    @JsonIgnore
    private Integer unumIsvalid;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    @JsonIgnore
    private Long unumEntryUid;

    private Long unumTemplCompItemId;

    private Long unumTempleCompId;

    private Long unumTempleHeadId;

    private Long unumTempleId;

    private Integer unumTempleIsmandy;

    private Long unumTempleItemId;

    private Long unumTempleSubheadId;

    @JsonIgnore
    private Integer unumUnivId;

    private String ustrTempledtlDescription;

    private Integer unumDisplayOrder;

    private Integer unumHideComponentTxt;

    private Integer unumHideHeaderTxt;

    private Integer unumHideItemTxt;

    private Integer unumPageColumns;
}
