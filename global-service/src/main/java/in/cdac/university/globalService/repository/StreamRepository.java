package in.cdac.university.globalService.repository;

import org.springframework.stereotype.Repository;

import in.cdac.university.globalService.entity.GmstStreamMst;
import in.cdac.university.globalService.entity.GmstStreamMstPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StreamRepository extends JpaRepository<GmstStreamMst, GmstStreamMstPK> {

	List<GmstStreamMst> findAllByunumIsvalid(int status);

	
}
