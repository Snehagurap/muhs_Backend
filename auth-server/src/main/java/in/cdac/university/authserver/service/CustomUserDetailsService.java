package in.cdac.university.authserver.service;

import in.cdac.university.authserver.bean.CustomUser;
import in.cdac.university.authserver.entity.UmmtUserMst;
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

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Checking the user: " + username);
        UmmtUserMst user = userRepository.findByUsername(1, username);
        if (user != null) {
            CustomUser customUser = new CustomUser();
            customUser.setUserId(user.getId().getGnumUserId().toString());
            customUser.setUsername(user.getGstrUsername());
            customUser.setPassword(user.getGstrPassword());
            customUser.setUserType(user.getGnumUserTypeId());
            customUser.setUserFullName(user.getId().getGstrUserFullName());
            customUser.setUniversityId(user.getGnumUniversityId());
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getGnumRoleId().toString());
            customUser.setAuthorities(List.of(authority));
            return customUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
