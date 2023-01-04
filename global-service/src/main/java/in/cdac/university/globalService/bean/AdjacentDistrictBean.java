package in.cdac.university.globalService.bean;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@ToString
public class AdjacentDistrictBean extends GlobalBean{

    @NotNull(message = "District is mandatory")
    private Integer numDistId;


    private Integer numAdjacentDistId;

    private Integer numAdjacencyPriority;

    private String ustrDescription;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Integer unumIsvalid;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private Date udtLstModDate;

    @NotNull(message = "State is mandatory")
    private Integer gnumStatecode;

    @NotNull(message = "Adjacent Districts to map are mandatory")
    private List<Integer> mappedAdjacentDistricts;


}
