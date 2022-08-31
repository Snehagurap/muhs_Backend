package in.cdac.university.authserver.config;

import in.cdac.university.authserver.bean.CustomUser;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenConvertor extends JwtAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

    @Override
    public void configure(JwtAccessTokenConverter converter) {
        converter.setAccessTokenConverter(this);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        System.out.println(map);
        AccessTokenMapper details = new AccessTokenMapper();
        if (map.containsKey("userId"))
            details.setUserId(Integer.valueOf(map.get("userId").toString()));
        if (map.containsKey("userType"))
            details.setUserType((Integer) map.get("userType"));
        if (map.containsKey("universityId"))
            details.setUniversityId((Integer) map.get("universityId"));
        if (map.containsKey("applicationType"))
            details.setApplicationType((Integer) map.get("applicationType"));
        authentication.setDetails(details);
        return authentication;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>(accessToken.getAdditionalInformation());
        if (customUser.getUserId() != null)
            info.put("userId", customUser.getUserId());
        if (customUser.getUserType() != null)
            info.put("userType", customUser.getUserType());
        if (customUser.getUniversityId() != null)
            info.put("universityId", customUser.getUniversityId());
        if (customUser.getApplicationType() != null)
            info.put("applicationType", customUser.getApplicationType());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        // TODO
        // Log token details
        return super.enhance(customAccessToken, authentication);
    }
}
