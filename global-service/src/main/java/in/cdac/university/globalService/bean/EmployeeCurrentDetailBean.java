package in.cdac.university.globalService.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter

public class EmployeeCurrentDetailBean {

    private Long unumEmpCurId;
    private Integer unumIsvalid;
    private String authorityBosAcadcounsilMgtcounsilSenate;
    private String lictlAcadyearChild;
    private String lictlDesigpg;
    private String lictlDesigug;
    private String lictlExpyearsPg;
    private String lictlExpyearsUg;
    private String lictlRank;
    private String lictlSelectionforaction;
    private String lictlSelectionpriority;
    private Date udtEntryDate;
    private Date udtFromDate;
    private Date udtPgJoiningDate;
    private Date udtToDate;
    private Date udtUgJoiningDate;
    private Long unumAuthorityRoleid;
    private Long unumDesigidPg;
    private Long unumDesigidUg;
    private Long unumEntryUid;
    private Integer unumRank;
    private Long unumSelectforactionRoleid;
    private Long unumSelectionPriorityId;
    private Long unumSelectionpriorityRoleid;
    private Integer unumEmpDesigid;
    private Long unumEmpId;
    private Integer unumUnivId;
    private String ustrRemarks;
    private String ustrTAadharNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeCurrentDetailBean that = (EmployeeCurrentDetailBean) o;
        return Objects.equals(unumEmpCurId, that.unumEmpCurId) && Objects.equals(unumIsvalid, that.unumIsvalid) && Objects.equals(authorityBosAcadcounsilMgtcounsilSenate, that.authorityBosAcadcounsilMgtcounsilSenate) && Objects.equals(lictlAcadyearChild, that.lictlAcadyearChild) && Objects.equals(lictlDesigpg, that.lictlDesigpg) && Objects.equals(lictlDesigug, that.lictlDesigug) && Objects.equals(lictlExpyearsPg, that.lictlExpyearsPg) && Objects.equals(lictlExpyearsUg, that.lictlExpyearsUg) && Objects.equals(lictlRank, that.lictlRank) && Objects.equals(lictlSelectionforaction, that.lictlSelectionforaction) && Objects.equals(lictlSelectionpriority, that.lictlSelectionpriority) && Objects.equals(unumAuthorityRoleid, that.unumAuthorityRoleid) && Objects.equals(unumDesigidPg, that.unumDesigidPg) && Objects.equals(unumDesigidUg, that.unumDesigidUg) && Objects.equals(unumEntryUid, that.unumEntryUid) && Objects.equals(unumRank, that.unumRank) && Objects.equals(unumSelectforactionRoleid, that.unumSelectforactionRoleid) && Objects.equals(unumSelectionPriorityId, that.unumSelectionPriorityId) && Objects.equals(unumSelectionpriorityRoleid, that.unumSelectionpriorityRoleid) && Objects.equals(unumEmpDesigid, that.unumEmpDesigid) && Objects.equals(unumEmpId, that.unumEmpId) && Objects.equals(unumUnivId, that.unumUnivId) && Objects.equals(ustrRemarks, that.ustrRemarks) && Objects.equals(ustrTAadharNo, that.ustrTAadharNo);
    }


}
