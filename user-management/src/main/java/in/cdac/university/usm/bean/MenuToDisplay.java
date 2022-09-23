package in.cdac.university.usm.bean;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuToDisplay {

    @JsonProperty("key")
    private Integer gnumMenuId;

    @JsonIgnore
    private Integer gnumParentId;

    @JsonProperty("label")
    private String gstrMenuName;

    @JsonProperty("to")
    private String gstrUrl;

    @JsonIgnore
    private String gstrModuleName;

    private String icon;

    @JsonProperty("children")
    private List<MenuToDisplay> subMenuList;

    public void setGstrUrl(String gstrUrl) {
        this.gstrUrl = gstrUrl == null ? "" : gstrUrl;
    }
}
