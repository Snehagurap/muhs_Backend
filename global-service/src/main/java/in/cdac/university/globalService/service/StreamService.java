package in.cdac.university.globalService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cdac.university.globalService.bean.StreamBean;
import in.cdac.university.globalService.bean.SubjectBean;
import in.cdac.university.globalService.repository.StreamRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;

@Service
public class StreamService {
	
	@Autowired
	private StreamRepository streamRepository;
	
	
	
	
	public List<StreamBean> getAllStream(int status) throws Exception {
     
		
		
		
		return BeanUtils.copyListProperties(
				streamRepository.findAllByunumIsvalid(status),
				StreamBean.class
        );
    }

}
