package in.cdac.university.usm.repository;

import in.cdac.university.usm.entity.UmmtUserTypeMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UmmtUserTypeMst, Integer> {

    List<UmmtUserTypeMst> findAllByGnumIsvalidAndUserCategoryMstGnumUserCatIdOrderByGstrUserTypeName(Integer isValid, Integer categoryId);

    UmmtUserTypeMst findByGstrUserTypeNameIgnoreCaseAndGnumIsvalidNot(String userType, Integer isValid);

    UmmtUserTypeMst findByGstrUserTypeNameIgnoreCaseAndGnumUserTypeIdNotAndGnumIsvalidNot(String userType, Integer userTypeId, Integer isValid);

    List<UmmtUserTypeMst> findAllByGnumUserTypeIdInAndGnumIsvalidNot(List<Integer> userTypeId, Integer isValid);

    Optional<UmmtUserTypeMst> findByGnumUserTypeIdAndGnumIsvalidNot(Integer userTypeId, Integer isValid);
}
