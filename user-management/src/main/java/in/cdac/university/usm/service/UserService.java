package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.UserBean;
import in.cdac.university.usm.entity.GmstUniversityMst;
import in.cdac.university.usm.entity.UmmtRoleMst;
import in.cdac.university.usm.entity.UmmtUserMst;
import in.cdac.university.usm.entity.UmmtUserMstPK;
import in.cdac.university.usm.repository.RoleRepository;
import in.cdac.university.usm.repository.UniversityRepository;
import in.cdac.university.usm.repository.UserRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private Language language;

    public List<UserBean> getAllUsersByUniversityId(Integer universityId, Integer isValid) {
        return BeanUtils.copyListProperties(
                userRepository.findAllByUniversityMstUnumUnivIdAndGnumIsvalidOrderByGstrUserName(universityId, isValid),
                UserBean.class
        );
    }

    public ServiceResponse get(Integer userId) {
        if (userId == null) {
            return ServiceResponse.errorResponse(language.mandatory("UserId"));
        }

        Optional<UmmtUserMst> userMst = userRepository.findById(new UmmtUserMstPK(userId, 1));
        if (userMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("User", userId));
        }

        UserBean userBean = BeanUtils.copyProperties(userMst.get(), UserBean.class);
        userBean.setUnumUnivId(userMst.get().getUniversityMst().getUnumUnivId());

        return ServiceResponse.builder()
                .status(1)
                .responeObject(userBean)
                .build();
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
        Optional<UmmtUserMst> userMstOptional = userRepository.findByGstrUserNameIgnoreCaseAndGnumIsvalidIn(userBean.getGstrUserName(), List.of(1, 2));
        if (userMstOptional.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Username", userBean.getGstrUserName()));
        }

        // Generate new User id
        Integer userId = userRepository.generateUserId();
        if (userId == null)
            userId = 1;
        log.info("New User Id generated " + userId);

        UmmtUserMst userMst = BeanUtils.copyProperties(userBean, UmmtUserMst.class);
        userMst.setUniversityMst(universityMst.get());
        userMst.setGnumUserid(userId);
        userMst = userRepository.save(userMst);
        int status = 0;
        String message;
        if (userMst.getGnumUserid() != null) {
            status = 1;
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
        Optional<UmmtUserMst> duplicateUser = userRepository.findByGstrUserNameIgnoreCaseAndGnumUseridNotAndGnumIsvalidIn(
                userBean.getGstrUserName(), userBean.getGnumUserid(), List.of(1, 2));
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
        List<Integer> userIdsToDelete = Arrays.stream(userBean.getIdsToDelete())
                .map(Integer::valueOf)
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

}
