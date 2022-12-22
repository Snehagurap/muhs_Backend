package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "table_name")
public class UmstColumnMst {
    @Id
    @Column
    private String tableColumnId;
    @Column
    private String tableColumnName;
}
