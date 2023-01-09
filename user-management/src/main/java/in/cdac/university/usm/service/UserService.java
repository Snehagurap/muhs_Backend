package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.UserBean;
import in.cdac.university.usm.entity.*;
import in.cdac.university.usm.repository.*;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private Language language;

    public List<UserBean> getAllUsersByUniversityId(Integer universityId, Integer isValid) {
        Map<Integer, String> userTypeMap = userTypeRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        UmmtUserTypeMst::getGnumUserTypeId,
                        UmmtUserTypeMst::getGstrUserTypeName
                ));

        return userRepository.findAllByUniversityMstUnumUnivIdAndGnumIsvalidOrderByGstrUserName(universityId, isValid)
                .stream()
                .map(ummtUserMst -> {
                    UserBean userBean = BeanUtils.copyProperties(ummtUserMst, UserBean.class);
                    userBean.setUserTypeName(userTypeMap.getOrDefault(ummtUserMst.getGnumUserTypeId(), ""));
                    return userBean;
                })
                .toList();
    }

    public ServiceResponse get(Long userId) {
        if (userId == null) {
            return ServiceResponse.errorResponse(language.mandatory("UserId"));
        }

        Optional<UmmtUserMst> userMst = userRepository.findById(new UmmtUserMstPK(userId, 1));
        if (userMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("User", userId));
        }

        UserBean userBean = BeanUtils.copyProperties(userMst.get(), UserBean.class);
        userBean.setUnumUnivId(userMst.get().getUniversityMst().getUnumUnivId());

        // GetDataSetName
        String dataSetName = datasetRepository.findById(userBean.getGnumDatasetId())
                .map(UmmtDatasetMst::getGstrDatasetName)
                .orElse("");
        userBean.setDefaultDataSetName(dataSetName);

        // Get Role Name
        String roleName = roleRepository.findById(userBean.getGnumRoleId())
                .map(UmmtRoleMst::getGstrRoleName)
                .orElse("");
        userBean.setDefaultRoleName(roleName);

        return ServiceResponse.successObject(userBean);
    }

    public ServiceResponse save(UserBean userBean) {
        // Check if university id is correct
        Optional<GmstUniversityMst> universityMst = universityRepository.findById(userBean.getUnumUnivId());
        if (universityMst.isEmpty() || universityMst.get().getUnumIsvalid() == 0) {
            return ServiceResponse.errorResponse(language.notFoundForId("University", userBean.getUnumUnivId()));
        }

        // Check if Role id is correct
        if (userBean.getGnumRoleId() != null) {
            Optional<UmmtRoleMst> roleMst = roleRepository.findById(userBean.getGnumRoleId());
            if (roleMst.isEmpty()) {
                return ServiceResponse.errorResponse(language.notFoundForId("Role", userBean.getGnumRoleId()));
            }
        }

        // Check for duplicate username
        Optional<UmmtUserMst> userMstOptional = userRepository.findByGstrUserNameIgnoreCase(userBean.getGstrUserName());
        if (userMstOptional.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Username", userBean.getGstrUserName()));
        }

        // Generate new User id
        Long userId = userRepository.generateUserId();
        if (userId == null)
            userId = 1L;
        log.info("New User Id generated " + userId);

        UmmtUserMst userMst = BeanUtils.copyProperties(userBean, UmmtUserMst.class);
        userMst.setUniversityMst(universityMst.get());
        userMst.setGnumUserid(userId);
        userMst = userRepository.save(userMst);
        int status = 0;
        String message;
        if (userMst.getGnumUserid() != null) {
            status = 1;
            // Save default role
            if (userMst.getGnumRoleId() != null) {
                UmmtUserRoleMst userRoleMst = new UmmtUserRoleMst();
                userRoleMst.setGnumRoleId(userMst.getGnumRoleId());
                userRoleMst.setGnumUserId(userMst.getGnumUserid());
                userRoleMst.setGnumIsDefault(1);
                userRoleMst.setGblIsvalid(1);
                userRoleRepository.save(userRoleMst);
            }

            message = language.saveSuccess("User");
        } else {
            message = language.saveError("User");
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse update(UserBean userBean) {
        // Check if User id is present
        if (userBean.getGnumUserid() == null) {
            return ServiceResponse.errorResponse(language.mandatory("User Id"));
        }

        Optional<UmmtUserMst> userMstOptional = userRepository.findById(new UmmtUserMstPK(userBean.getGnumUserid(), 1));
        if (userMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("User", userBean.getGnumUserid()));
        }

        // Check if university id is correct
        Optional<GmstUniversityMst> universityMst = universityRepository.findById(userBean.getUnumUnivId());
        if (universityMst.isEmpty() || universityMst.get().getUnumIsvalid() == 0) {
            return ServiceResponse.errorResponse(language.notFoundForId("University", userBean.getUnumUnivId()));
        }

        // Check if Role id is correct
        if (userBean.getGnumRoleId() != null) {
            Optional<UmmtRoleMst> roleMst = roleRepository.findById(userBean.getGnumRoleId());
            if (roleMst.isEmpty()) {
                return ServiceResponse.errorResponse(language.notFoundForId("Role", userBean.getGnumRoleId()));
            }
        }

        // Check for duplicate username
        Optional<UmmtUserMst> duplicateUser = userRepository.findByGstrUserNameIgnoreCaseAndGnumUseridNot(
                userBean.getGstrUserName(), userBean.getGnumUserid());
        if (duplicateUser.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Username", userBean.getGstrUserName()));
        }

        UmmtUserMst oldUser = userMstOptional.get();

        int noOfRowsUpdated = userRepository.createLog(userBean.getGnumUserid());

        if (noOfRowsUpdated == 0) {
            return ServiceResponse.errorResponse(language.updateError("User"));
        }

        UmmtUserMst userMst = BeanUtils.copyProperties(userBean, UmmtUserMst.class);
        userMst.setUniversityMst(universityMst.get());
        userMst.setGdtEntrydate(oldUser.getGdtEntrydate());
        userMst.setGnumEntryBy(oldUser.getGnumEntryBy());
        userMst.setGnumIslock(oldUser.getGnumIslock());
        userMst.setGstrOldPassword(oldUser.getGstrOldPassword());
        userMst.setGdtChangepasswordDate(oldUser.getGdtChangepasswordDate());
        userMst.setGnumLastLoginDate(oldUser.getGnumLastLoginDate());
        userMst = userRepository.save(userMst);
        int status = 0;
        String message;
        if (userMst.getGnumUserid() != null) {
            status = 1;
            // Save default role
            if (userMst.getGnumRoleId() != null) {
                List<UmmtUserRoleMst> mappedRoles = userRoleRepository.findMappedRoles(
                        userMst.getGnumUserid(), 1, 1, userMst.getGnumRoleId());
                if (!mappedRoles.isEmpty())
                    userRoleRepository.deleteAllInBatch(mappedRoles);

                UmmtUserRoleMst userRoleMst = new UmmtUserRoleMst();
                userRoleMst.setGnumRoleId(userMst.getGnumRoleId());
                userRoleMst.setGnumUserId(userMst.getGnumUserid());
                userRoleMst.setGnumIsDefault(1);
                userRoleMst.setGblIsvalid(1);
                userRoleRepository.save(userRoleMst);
            }

            message = language.updateSuccess("User");
        } else {
            message = language.updateError("User");
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse delete(UserBean userBean) {
        // Check if User id is present
        if (userBean.getIdsToDelete() == null || userBean.getIdsToDelete().length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("User Id"));
        }
        List<Long> userIdsToDelete = Arrays.stream(userBean.getIdsToDelete())
                .map(Long::valueOf)
                .toList();

        List<UmmtUserMst> userMstList = userRepository.findAllByGnumUseridInAndGnumIsvalidIn(
            userIdsToDelete, List.of(1, 2)
        );

        if (userMstList.isEmpty() || userMstList.size() != userBean.getIdsToDelete().length) {
            return ServiceResponse.errorResponse(language.notFoundForId("User Id", Arrays.toString(userBean.getIdsToDelete())));
        }

        int noOfRowsUpdated = userRepository.deleteUsers(userBean.getGnumLstmodBy(), userIdsToDelete);
        int status = 0;
        String message;
        if (noOfRowsUpdated > 0) {
            status = 1;
            message = language.deleteSuccess("User");
        } else {
            message = language.deleteError("User");
        }

        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public List<UserBean> getUsersForCategory(Integer categoryId) {
        return BeanUtils.copyListProperties(
                userRepository.findByGnumUserCatIdAndGnumIsvalid(categoryId, 1),
                UserBean.class
        );
    }
    public List<UserBean> getUserCombo(Integer userCategoryId, Integer universityId){
        return BeanUtils.copyListProperties(userRepository.findByGnumUserCatIdAndUniversityMstUnumUnivIdAndGnumIsvalidOrderByGstrUserFullNameAsc(userCategoryId,universityId,1),UserBean.class);
    }
}
