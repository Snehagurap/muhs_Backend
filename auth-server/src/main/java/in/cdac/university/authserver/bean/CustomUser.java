package in.cdac.university.authserver.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class CustomUser implements UserDetails {
    private String userId;
    private String username;
    private String password;
    private Integer universityId;
    private String userFullName;
    private String role;
    private Integer userType;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private Integer applicationType;
    private Integer userCategory;
}
