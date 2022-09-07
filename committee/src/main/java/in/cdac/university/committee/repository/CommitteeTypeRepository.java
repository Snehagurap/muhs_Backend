package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeTypeMst;
import in.cdac.university.committee.entity.GbltCommitteeTypeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitteeTypeRepository extends JpaRepository<GbltCommitteeTypeMst, GbltCommitteeTypeMstPK> {

    List<GbltCommitteeTypeMst> findByUnumIsvalidOrderByUstrComtypeFnameAsc(Integer unumIsvalid);

}
