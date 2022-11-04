package in.cdac.university.authserver.config;

import in.cdac.university.authserver.bean.CustomUser;
import in.cdac.university.authserver.entity.GbltUserLog;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Date;
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
        AccessTokenMapper details = new AccessTokenMapper();
        if (map.containsKey("userId"))
            details.setUserId(Long.valueOf(map.get("userId").toString()));
        if (map.containsKey("userType"))
            details.setUserType((Integer) map.get("userType"));
        if (map.containsKey("universityId"))
            details.setUniversityId((Integer) map.get("universityId"));
        if (map.containsKey("applicationType"))
            details.setApplicationType((Integer) map.get("applicationType"));
        if (map.containsKey("userCategory"))
            details.setUserCategory((Integer) map.get("userCategory"));
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
        if (customUser.getUserCategory() != null)
            info.put("userCategory", customUser.getUserCategory());
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        // TODO
        // Log user login details
        GbltUserLog gbltUserLog = new GbltUserLog();
        gbltUserLog.setGnumUserid(Long.valueOf(customUser.getUserId()));
        gbltUserLog.setUnumUnivId(customUser.getUniversityId());
        gbltUserLog.setGdtLoginDate(new Date());
        gbltUserLog.setGstrIpNumber("");

        return super.enhance(customAccessToken, authentication);
    }
}
