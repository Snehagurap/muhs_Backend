package in.cdac.university.committee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cdac.university.committee.entity.GbltLicCommitteeMst;
import in.cdac.university.committee.entity.GbltLicCommitteeMstPK;

@Repository
public interface LicCommitteetMstRespository extends JpaRepository<GbltLicCommitteeMst, GbltLicCommitteeMstPK> {

}
