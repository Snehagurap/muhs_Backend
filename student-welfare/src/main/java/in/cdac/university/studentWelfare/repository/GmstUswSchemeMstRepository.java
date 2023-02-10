package in.cdac.university.studentWelfare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cdac.university.studentWelfare.entity.GmstUswSchemeMst;
import in.cdac.university.studentWelfare.entity.GmstUswSchemeMstPK;



public interface GmstUswSchemeMstRepository extends JpaRepository<GmstUswSchemeMst, GmstUswSchemeMstPK> {


	GmstUswSchemeMst findByUnumSchemeIdAndUnumIsvalidAndUnumUnivId(Long schemeNo, int i, Integer universityId);


}
