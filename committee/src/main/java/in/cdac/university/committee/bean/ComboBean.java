package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
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
