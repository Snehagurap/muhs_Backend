package in.cdac.university.committee.repository;

import in.cdac.university.committee.entity.GbltCommitteeDtl;
import in.cdac.university.committee.entity.GbltCommitteeDtlPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitteeDetailRepository extends JpaRepository<GbltCommitteeDtl, GbltCommitteeDtlPK> {
    List<GbltCommitteeDtl> findByUnumIsvalidAndUnumComidAndUnumUnivId(Integer unumIsvalid, Long unumComid, Integer unumUnivId);

}
