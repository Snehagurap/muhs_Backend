package in.cdac.university.authserver.service;

import in.cdac.university.authserver.bean.CustomUser;
import in.cdac.university.authserver.entity.UmmtUserMst;
import in.cdac.university.authserver.entity.UmstSystemUserMst;
import in.cdac.university.authserver.repository.SystemUserRepository;
import in.cdac.university.authserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Checking the user: " + username);
        String applicationType = username.split("##")[0];
        username = username.split("##")[1];

        CustomUser user = switch (applicationType) {
            case "1" ->
                // Application user
                    findApplicationUser(username);
            case "2" ->
                // User Management User
                    findUserManagementUser(username);
            case "3" ->
                // User Management Super User
                    findUserManagementSuperUser(username);
            default -> throw new UsernameNotFoundException("User not found");
        };
        if (user != null)
            user.setApplicationType(Integer.valueOf(applicationType));
        return user;
    }

    private CustomUser findUserManagementSuperUser(String username) {
        UmstSystemUserMst systemUser = systemUserRepository.findByGstrSysLoginIdAndGblIsvalid(username, 1);
        if (systemUser != null) {
            CustomUser customUser = new CustomUser();
            customUser.setUserId(systemUser.getGnumSysUserId().toString());
            customUser.setUsername(systemUser.getGstrSysLoginId());
            customUser.setPassword(systemUser.getGstrSysPassword());
            customUser.setUserType(1);
            customUser.setUserFullName(systemUser.getGstrSysUserName());
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("Super");
            customUser.setAuthorities(List.of(authority));
            return customUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private CustomUser findUserManagementUser(String username) {
        return null;
    }

    private CustomUser findApplicationUser(String username) {
        UmmtUserMst user = userRepository.findUser(username, 1);
        if (user != null) {
            CustomUser customUser = new CustomUser();
            customUser.setUserId(user.getId().getGnumUserId().toString());
            customUser.setUsername(user.getGstrUsername());
            customUser.setPassword(user.getGstrPassword());
            customUser.setUserType(user.getGnumUserTypeId());
            customUser.setUserFullName(user.getGstrUserFullName());
            customUser.setUniversityId(user.getGnumUniversityId());
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getGnumRoleId().toString());
            customUser.setAuthorities(List.of(authority));
            return customUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
