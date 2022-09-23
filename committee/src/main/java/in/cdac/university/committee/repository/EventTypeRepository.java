package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltEventtypeMst;
import in.cdac.university.committee.entity.GbltEventtypeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTypeRepository extends JpaRepository<GbltEventtypeMst, GbltEventtypeMstPK> {

    List<GbltEventtypeMst> findByUnumIsvalidAndUnumUnivIdOrderByUstrEventTypenameAsc(Integer unumIsvalid, Integer unumUnivId);

}
