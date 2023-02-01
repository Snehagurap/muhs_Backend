package in.cdac.university.globalService.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.globalService.bean.StudentMasterBean;
import in.cdac.university.globalService.entity.GmstStudentMst;
import in.cdac.university.globalService.repository.StudentRepository;

import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import in.cdac.university.globalService.util.BeanUtils;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Transactional
	public ServiceResponse saveUpdate(@Valid StudentMasterBean studentMasterBean) throws Exception {
		
        Date d1 = new Date();
        studentMasterBean.setUdtEntryDate(d1);
        studentMasterBean.setUdtEffFrom(d1);
        studentMasterBean.setUnumIsvalid(1);
        studentMasterBean.setUnumUnivId(RequestUtility.getUniversityId());
        studentMasterBean.setUnumEntryUid(RequestUtility.getUserId());
        
		if(studentMasterBean.getUnumStudentId() == 1L) { //save
			studentMasterBean.setUnumStudentId(studentRepository.getNextId());
		}
		else { //update
			studentRepository.createLog(List.of (studentMasterBean.getUnumStudentId()));
		}
		GmstStudentMst gmstStudentMst = BeanUtils.copyProperties(studentMasterBean, GmstStudentMst.class);
		studentRepository.save(gmstStudentMst);
		return ServiceResponse.successObject(studentMasterBean);
	}

	public ServiceResponse getStudentDetails(String studentEnrollmentNo ) throws Exception {
		GmstStudentMst gmstStudentMst = studentRepository.findByUstrEnrollmentNoIgnoreCaseAndUnumUnivIdAndUnumIsvalid(studentEnrollmentNo,RequestUtility.getUniversityId(),1);
		StudentMasterBean studentMasterBean = null ;
		if(gmstStudentMst!=null) {
			studentMasterBean =BeanUtils.copyProperties(gmstStudentMst, StudentMasterBean.class);
		}
		return ServiceResponse.successObject(studentMasterBean);
	}
}
