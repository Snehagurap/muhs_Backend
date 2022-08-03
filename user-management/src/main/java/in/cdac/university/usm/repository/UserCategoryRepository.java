package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmstUserCategoryMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCategoryRepository extends JpaRepository<UmstUserCategoryMst, Integer> {
}
