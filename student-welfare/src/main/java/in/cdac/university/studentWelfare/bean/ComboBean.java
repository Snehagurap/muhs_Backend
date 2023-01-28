package in.cdac.university.studentWelfare.bean;

import in.cdac.university.studentWelfare.util.annotations.ComboKey;
import in.cdac.university.studentWelfare.util.annotations.ComboValue;
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
