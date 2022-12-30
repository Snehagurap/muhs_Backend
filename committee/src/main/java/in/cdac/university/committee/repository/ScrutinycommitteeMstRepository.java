package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltScrutinycommitteeMst;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMstPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrutinycommitteeMstRepository extends JpaRepository<GbltScrutinycommitteeMst, GbltScrutinycommitteeMstPK> {
}
