package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.UserTypeBean;
import in.cdac.university.usm.entity.UmmtRoleMst;
import in.cdac.university.usm.entity.UmmtUserTypeMst;
import in.cdac.university.usm.entity.UmstUserCategoryMst;
import in.cdac.university.usm.repository.RoleRepository;
import in.cdac.university.usm.repository.UserCategoryRepository;
import in.cdac.university.usm.repository.UserTypeRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Language language;

    public List<UserTypeBean> getAllUserTypeList(Integer isValid, Integer categoryId) {
        return userTypeRepository.findAllByGnumIsvalidAndUserCategoryMstGnumUserCatIdOrderByGstrUserTypeName(isValid, categoryId)
                .stream()
                .map(userTypeEntity -> {
                    UserTypeBean userTypeBean = BeanUtils.copyProperties(userTypeEntity, UserTypeBean.class);
                    userTypeBean.setGnumRoleId(userTypeEntity.getRoleMst().getGnumRoleId());
                    userTypeBean.setDefaultRoleName(userTypeEntity.getRoleMst().getGstrRoleName());
                    return userTypeBean;
                })
                .collect(Collectors.toList());
    }

    private ServiceResponse saveUserCategory(UserTypeBean userTypeBean, UmstUserCategoryMst userCategory, UmmtRoleMst roleMst) {
        UmmtUserTypeMst userTypeMst = BeanUtils.copyProperties(userTypeBean, UmmtUserTypeMst.class);
        userTypeMst.setUserCategoryMst(userCategory);
        userTypeMst.setRoleMst(roleMst);
        int status = 0;
        String message;
        userTypeMst = userTypeRepository.save(userTypeMst);
        if (userTypeMst.getGnumUserTypeId() != null) {
            status = 1;
            message = language.saveSuccess("Data");
        } else {
            message = language.saveError("Data");
        }

        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse save(UserTypeBean userTypeBean) {
        // Check for duplicate User Type
        UmmtUserTypeMst userType = userTypeRepository.findByGstrUserTypeNameIgnoreCaseAndGnumIsvalidNot(userTypeBean.getGstrUserTypeName(), 0);
        if (userType != null) {
            return ServiceResponse.errorResponse(language.duplicate("User Type Name", userTypeBean.getGstrUserTypeName()));
        }

        // Check for user Category
        Optional<UmstUserCategoryMst> userCategory = userCategoryRepository.findById(userTypeBean.getGnumUserCatId());
        if (userCategory.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("User Type", userTypeBean.getGnumUserCatId()));
        }

        // Check for Role
        Optional<UmmtRoleMst> roleMst = roleRepository.findById(userTypeBean.getGnumRoleId());
        if (roleMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Role", userTypeBean.getGnumRoleId()));
        }

        return saveUserCategory(userTypeBean, userCategory.get(), roleMst.get());
    }

    public ServiceResponse update(UserTypeBean userTypeBean) {
        if (userTypeBean.getGnumUserTypeId() == null) {
            return ServiceResponse.errorResponse(language.message("mandatory", "User Type Id"));
        }

        Optional<UmmtUserTypeMst> userTypeMst = userTypeRepository.findByGnumUserTypeIdAndGnumIsvalidNot(userTypeBean.getGnumUserTypeId(), 0);
        if (userTypeMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("User Type", userTypeBean.getGnumUserTypeId()));
        }

        // Check for duplicate User Type
        UmmtUserTypeMst userType = userTypeRepository.findByGstrUserTypeNameIgnoreCaseAndGnumUserTypeIdNotAndGnumIsvalidNot(userTypeBean.getGstrUserTypeName(), userTypeBean.getGnumUserTypeId(), 0);
        if (userType != null) {
            return ServiceResponse.errorResponse(language.duplicate("User Type Name", userTypeBean.getGstrUserTypeName()));
        }

        // Check for user Category
        Optional<UmstUserCategoryMst> userCategory = userCategoryRepository.findById(userTypeBean.getGnumUserCatId());
        if (userCategory.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Category", userTypeBean.getGnumUserCatId()));
        }

        // Check for Role
        Optional<UmmtRoleMst> roleMst = roleRepository.findById(userTypeBean.getGnumRoleId());
        if (roleMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Role", userTypeBean.getGnumRoleId()));
        }

        return saveUserCategory(userTypeBean, userCategory.get(), roleMst.get());
    }

    public ServiceResponse delete(UserTypeBean userTypeBean) {
        if (userTypeBean.getIdsToDelete() == null || userTypeBean.getIdsToDelete().length == 0) {
            return ServiceResponse.errorResponse(language.message("mandatory", "User Type Id"));
        }

        List<Integer> idsToDelete = Stream.of(userTypeBean.getIdsToDelete())
                .map(Integer::valueOf)
                .toList();

        List<UmmtUserTypeMst> userTypeMstList = userTypeRepository.findAllByGnumUserTypeIdInAndGnumIsvalidNot(idsToDelete, 0);
        if (userTypeMstList.isEmpty() || userTypeMstList.size() != userTypeBean.getIdsToDelete().length) {
            return ServiceResponse.errorResponse(language.notFoundForId("User Type", Arrays.toString(userTypeBean.getIdsToDelete())));
        }

        for (UmmtUserTypeMst userType : userTypeMstList) {
            userType.setGnumIsvalid(0);
            userType.setGnumEntryBy(userTypeBean.getGnumEntryBy());
        }
        userTypeRepository.saveAll(userTypeMstList);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Data"))
                .build();
    }

    public ServiceResponse getUserType(Integer userTypeId) {
        if (userTypeId == null) {
            return ServiceResponse.errorResponse(language.message("mandatory", "User Type Id"));
        }

        Optional<UmmtUserTypeMst> userTypeMst = userTypeRepository.findByGnumUserTypeIdAndGnumIsvalidNot(userTypeId, 0);
        if (userTypeMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("User Type", userTypeId));
        }
        UmmtUserTypeMst ummtUserTypeMst = userTypeMst.get();
        UserTypeBean userTypeBean = BeanUtils.copyProperties(ummtUserTypeMst, UserTypeBean.class);
        userTypeBean.setGnumUserCatId(ummtUserTypeMst.getUserCategoryMst().getGnumUserCatId());
        userTypeBean.setGnumRoleId(ummtUserTypeMst.getRoleMst().getGnumRoleId());

        return ServiceResponse.builder()
                .status(1)
                .responeObject(userTypeBean)
                .build();
    }
}
