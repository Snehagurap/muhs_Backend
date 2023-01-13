package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstDropdownMstPK.class)
@Entity
@Table(name="gmst_dropdown_mst", schema = "university")
public class GmstDropdownMst implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumDropdownId;

	@Id
	private Long unumDropdownItemId;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_dropdown_item_name")
	private String ustrDropdownItemName;

	@Column(name="unum_order_no")
	private Double unumOrderNo;

}