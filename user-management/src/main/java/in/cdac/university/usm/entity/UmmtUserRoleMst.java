package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "ummt_user_role_mst", schema = "usm",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "uk_ummt_user_role_mst",
						columnNames = {"gnum_role_id", "gnum_userid"}
				)
		})
public class UmmtUserRoleMst implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ummt_user_role_mst")
	@SequenceGenerator(name="ummt_user_role_mst", sequenceName = "usm.seq_ummt_user_role_mst", allocationSize = 1)
	@Column(name = "gnum_user_role_slno", nullable = false)
	private Long gnumUserRoleSlno;
	
	@Column(name = "gnum_role_id", nullable = false)
	private Integer gnumRoleId;

	@Column(name = "gnum_userid", nullable = false)
	private Integer gnumUserId;
	
	@Column(name = "gnum_is_default",nullable = false)
	private Integer gnumIsDefault;
	
	@Column(name = "gbl_isvalid", nullable = false)
	private Integer gblIsvalid;
	
	@Formula("(select u.gstr_role_name from usm.ummt_role_mst u where u.gnum_role_id = gnum_role_id)")
	private String roleName;
}