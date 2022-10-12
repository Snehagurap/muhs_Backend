package in.cdac.university.planningBoard.bean;

import in.cdac.university.planningBoard.util.annotations.ComboKey;
import in.cdac.university.planningBoard.util.annotations.ComboValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboBean {
    @ComboKey
    String key;

    @ComboValue
    String value;
}
