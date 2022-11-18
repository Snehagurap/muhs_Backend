package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.CustomUser;
import in.cdac.university.usm.entity.GbltUserLog;
import in.cdac.university.usm.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserLogService {

    @Autowired
    private UserLogRepository userLogRepository;

    public CustomUser logUserLoginDetails(CustomUser customUser) {
        // Log user login details
        GbltUserLog gbltUserLog = new GbltUserLog();
        gbltUserLog.setGnumUserid(Long.valueOf(customUser.getUserId()));
        gbltUserLog.setUnumUnivId(customUser.getUniversityId());
        gbltUserLog.setGdtLoginDate(new Date());
        gbltUserLog.setGstrIpNumber(customUser.getIpAddress());
        gbltUserLog = userLogRepository.save(gbltUserLog);
        customUser.setGnumLogId(gbltUserLog.getGnumLogId());
        return customUser;
    }

    public CustomUser logout(CustomUser customUser) {
        userLogRepository.logoutUser(Long.valueOf(customUser.getUserId()), customUser.getIpAddress(), customUser.getGnumLogId());
        return customUser;
    }
}
