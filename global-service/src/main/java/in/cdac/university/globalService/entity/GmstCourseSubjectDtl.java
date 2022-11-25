package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstCourseSubjectDtlPK.class)
@Table(name="gmst_course_subject_dtl", schema = "university")
public class GmstCourseSubjectDtl implements Serializable {

    @Id
    private Long unumCourseSubId;

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

    @Column(name="unum_univ_id")
    private Integer unumUnivId;

    @Column(name="unum_course_id")
    private Long unumCourseId;

    @Column(name="unum_sub_id")
    private Long unumSubId;

    @Column(name="ustr_description")
    private Long ustrDescription;
}
