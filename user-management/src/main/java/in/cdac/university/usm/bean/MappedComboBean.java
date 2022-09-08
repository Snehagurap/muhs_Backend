package in.cdac.university.usm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappedComboBean {
    private List<ComboBean> mapped;
    private List<ComboBean> unmapped;
}
