package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Getter
@Setter
@Entity
public class UmstTableMst {
    @Id
    @Column
    private String tablenameid;
    @Column
    private String tablename;
}
