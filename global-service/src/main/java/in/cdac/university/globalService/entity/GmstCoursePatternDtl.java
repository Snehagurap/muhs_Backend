package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstCoursePatternDtlPK.class)
@Entity
@Table(name = "gmst_course_pattern_dtl", schema = "university")
public class GmstCoursePatternDtl implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long unumCoursePatId;

    @Id
    private Integer unumIsvalid;

    @Temporal(TemporalType.DATE)
    @Column(name = "udt_eff_from")
    private Date udtEffFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "udt_eff_to")
    private Date udtEffTo;

    @Column(name = "udt_entry_date")
    private Date udtEntryDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "udt_lst_mod_date")
    private Date udtLstModDate;

    @Column(name = "unum_cfaculty_id")
    private Integer unumCfacultyId;

    @Column(name = "unum_course_id")
    private Long unumCourseId;

    @Column(name = "unum_ctype_id")
    private Long unumCtypeId;

    @Column(name = "unum_dept_id")
    private Integer unumDeptId;

    @Column(name = "unum_entry_uid")
    private Long unumEntryUid;

    @Column(name = "unum_lst_mod_uid")
    private Long unumLstModUid;

    @Column(name = "unum_stream_id")
    private Long unumStreamId;

    @Column(name = "unum_univ_id")
    private Integer unumUnivId;

    @Column(name = "ustr_course_code")
    private String ustrCourseCode;

    @Column(name = "ustr_course_pat_code")
    private String ustrCoursePatCode;

    @Column(name = "ustr_course_pat_doc_path")
    private String ustrCoursePatDocPath;

    @Column(name = "ustr_course_pat_fname")
    private String ustrCoursePatFname;

    @Column(name = "ustr_course_pat_sname")
    private String ustrCoursePatSname;

    @Column(name = "ustr_description")
    private String ustrDescription;

    @Column(name = "ustr_reason_for_new_pat")
    private String ustrReasonForNewPat;

}