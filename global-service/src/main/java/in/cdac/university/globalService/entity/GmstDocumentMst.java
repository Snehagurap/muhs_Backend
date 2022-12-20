package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstDocumentMstPK.class)
@Entity
@Table(name="gmst_document_mst", schema = "university")
public class GmstDocumentMst implements Serializable {

	@Id
	private Long unumDocId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_lst_mod_dt")
	private Date udtLstModDt;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_doc_code")
	private String ustrDocCode;

	@Column(name="ustr_doc_name")
	private String ustrDocName;

}