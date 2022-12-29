package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_process_mst", schema = "university")
public class GmstProcessMst implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="unum_process_id")
	private Integer unumProcessId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_dt")
	private Date udtLstModDt;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_process_code")
	private String ustrProcessCode;

	@Column(name="ustr_process_name")
	private String ustrProcessName;

}