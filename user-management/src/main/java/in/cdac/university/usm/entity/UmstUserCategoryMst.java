package in.cdac.university.usm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "umst_user_category_mst", schema = "usm")
public class UmstUserCategoryMst implements java.io.Serializable {

	@Id
	@Column(name = "gnum_user_cat_id", unique = true, nullable = false)
	private Integer gnumUserCatId;

	@Column(name = "gstr_user_cat_name", nullable = false, length = 200)
	private String gstrUserCatName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_entry_date", insertable = false, nullable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntryDate;

	@Column(name = "gnum_isvalid", nullable = false, columnDefinition = "numeric default 1")
	private Integer gnumIsvalid;
}
