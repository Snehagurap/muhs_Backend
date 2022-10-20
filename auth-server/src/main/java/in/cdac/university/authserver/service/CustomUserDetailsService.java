package in.cdac.university.authserver.service;

import in.cdac.university.authserver.bean.CustomUser;
import in.cdac.university.authserver.entity.GmstApplicantDraftMst;
import in.cdac.university.authserver.entity.GmstUniversityMst;
import in.cdac.university.authserver.entity.UmmtUserMst;
import in.cdac.university.authserver.entity.UmstSystemUserMst;
import in.cdac.university.authserver.repository.DraftApplicantRepository;
import in.cdac.university.authserver.repository.SystemUserRepository;
import in.cdac.university.authserver.repository.UserManagementUserRepository;
import in.cdac.university.authserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private UserManagementUserRepository userManagementUserRepository;

    @Autowired
    private DraftApplicantRepository draftApplicantRepository;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Checking the user: " + username);
        String applicationType = "1";
        String userCategory = "1";
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            applicationType = request.getHeader("applicationType");
            userCategory = request.getHeader("userCategory");
            log.debug("Application Type: {}", applicationType);
            log.debug("User Category: {}", userCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (applicationType) {
            case "1":
                if (userCategory != null && userCategory.equals("2")) {
                    return findDraftApplicant(username);
                } else {
                    // Application user
                    return findApplicationUser(username);
                }
            case "2":
                // User Management User
                return findUserManagementUser(username);
            case "3":
                // User Management Super User
                return findUserManagementSuperUser(username);
        }

        throw new UsernameNotFoundException("User not found");
    }

    private CustomUser findUserManagementSuperUser(String username) {
        Optional<UmstSystemUserMst> systemUserOptional = systemUserRepository.findByGstrSysLoginIdAndGblIsvalid(username, 1);
        if (systemUserOptional.isPresent()) {
            UmstSystemUserMst systemUser = systemUserOptional.get();
            CustomUser customUser = new CustomUser();
            customUser.setUserId(systemUser.getGnumSysUserId().toString());
            customUser.setUsername(systemUser.getGstrSysLoginId());
            customUser.setPassword(systemUser.getGstrSysPassword());
            customUser.setUserType(1);
            customUser.setUserCategory(0);
            customUser.setUserFullName(systemUser.getGstrSysUserName());
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("Super");
            customUser.setAuthorities(List.of(authority));
            return customUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private CustomUser findUserManagementUser(String username) {
        Optional<GmstUniversityMst> universityMstOptional = userManagementUserRepository.findByGstrUserNameAndUnumIsvalid(username, 1);
        if (universityMstOptional.isPresent()) {
            GmstUniversityMst universityMst = universityMstOptional.get();
            CustomUser customUser = new CustomUser();
            customUser.setUserId(universityMst.getUnumUnivId().toString());
            customUser.setUsername(universityMst.getGstrUserName());
            customUser.setPassword(universityMst.getGstrPassword());
            customUser.setUserType(2);
            customUser.setUserCategory(0);
            customUser.setUserFullName(universityMst.getUstrUnivFname());
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("User");
            customUser.setAuthorities(List.of(authority));
            return customUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private CustomUser findApplicationUser(String username) {
        Optional<UmmtUserMst> userOptional = userRepository.findUser(username, 1);
        if (userOptional.isPresent()) {
            UmmtUserMst user = userOptional.get();
            CustomUser customUser = new CustomUser();
            customUser.setUserId(user.getGnumUserid().toString());
            customUser.setUsername(user.getGstrUserName());
            customUser.setPassword(user.getGstrPassword());
            customUser.setUserType(user.getGnumUserTypeId());
            customUser.setUserFullName(user.getGstrUserFullName());
            customUser.setUserCategory(1);
            customUser.setUniversityId(user.getUniversityMst().getUnumUnivId());
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getGnumRoleId().toString());
            customUser.setAuthorities(List.of(authority));
            return customUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private CustomUser findDraftApplicant(String username) {
        log.debug("Applicant Username: {}", username);
        Optional<GmstApplicantDraftMst> userOptional = draftApplicantRepository.findUser(username, 1);
        if (userOptional.isPresent()) {
            GmstApplicantDraftMst user = userOptional.get();
            log.debug("Applicant: {}", user);
            CustomUser customUser = new CustomUser();
            customUser.setUserId(user.getUnumApplicantDraftid().toString());
            customUser.setUsername(user.getUstrTempUid());
            customUser.setPassword(user.getUstrTempPass());
            customUser.setUserType(5);
            customUser.setUserFullName(user.getUstrApplicantName());
            customUser.setUniversityId(1);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("1");
            customUser.setAuthorities(List.of(authority));
            customUser.setUserCategory(5);
            return customUser;
        } else {
            log.debug("No applicant Found");
            throw new UsernameNotFoundException("User not found");
        }
    }
}
