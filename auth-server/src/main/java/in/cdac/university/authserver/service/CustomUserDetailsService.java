package in.cdac.university.authserver.service;

import in.cdac.university.authserver.bean.CustomUser;
import in.cdac.university.authserver.entity.*;
import in.cdac.university.authserver.repository.*;
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

    @Autowired
    private ApplicantRepository applicantRepository;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Checking the user: {}", username);
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

        CustomUser customUser = null;
        switch (applicationType) {
            case "1":
                if (userCategory != null && userCategory.equals("2")) {
                    customUser = findApplicant(username);
                } else {
                    // Application user
                    customUser = findApplicationUser(username);
                }
                break;
            case "2":
                // User Management User
                customUser = findUserManagementUser(username);
                break;
            case "3":
                // User Management Super User
                customUser = findUserManagementSuperUser(username);
        }
        if (customUser != null) {
            customUser.setApplicationType(Integer.parseInt(applicationType));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return customUser;
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

    private CustomUser findApplicant(String username) {
        log.debug("Applicant Username: {}", username);
        // Check for Draft Applicant
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
            // Check for Applicant
            Optional<GmstApplicantMst> applicantMstOptional = applicantRepository.findByUnumIsvalidAndUstrUid(1, username);
            if (applicantMstOptional.isPresent()) {
                GmstApplicantMst applicantMst = applicantMstOptional.get();
                CustomUser customUser = new CustomUser();
                customUser.setUserId(applicantMst.getUnumApplicantId().toString());
                customUser.setUsername(applicantMst.getUstrUid());
                customUser.setPassword(applicantMst.getUstrPass());
                customUser.setUserType(5);
                customUser.setUserFullName(applicantMst.getUstrApplicantName());
                customUser.setUniversityId(1);
                customUser.setUserCategory(6);
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("1");
                customUser.setAuthorities(List.of(authority));
                return customUser;
            } else {
                log.debug("No applicant Found");
                throw new UsernameNotFoundException("User not found");
            }
        }
    }
}
