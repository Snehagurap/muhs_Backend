package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeDtl;
import in.cdac.university.committee.entity.GbltCommitteeDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitteeDetailRepository extends JpaRepository<GbltCommitteeDtl, GbltCommitteeDtlPK> {

}
