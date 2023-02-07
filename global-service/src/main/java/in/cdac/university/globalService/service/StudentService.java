package in.cdac.university.globalService.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.globalService.bean.StudentMasterBean;
import in.cdac.university.globalService.entity.GmstStudentMst;
import in.cdac.university.globalService.entity.GmstStudentMstPK;
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
        studentMasterBean.setUnumUnivId(RequestUtility.getUniversityId());
        studentMasterBean.setUnumEntryUid(RequestUtility.getUserId());
        
        
		if(studentMasterBean.getUnumStudentId() == 1L) { //save
			studentMasterBean.setUnumStudentId(studentRepository.getNextId());
		}
		else if(studentMasterBean.getUnumIsvalid() != null){
			if(studentMasterBean.getUnumIsvalid() == 1) {
				GmstStudentMst gmstStudentMst =  studentRepository.findByUnumStudentIdAndUnumUnivIdAndUnumIsvalid(studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId(),1);
				StudentMasterBean StudentMasterTemp =  BeanUtils.copyProperties(gmstStudentMst, StudentMasterBean.class);
				if(! studentMasterBean.equals(StudentMasterTemp))
					studentRepository.createLog(List.of(studentMasterBean.getUnumStudentId()));
			}
		}
		else { //update
			//to remove the draft data
			GmstStudentMstPK gmstStudentMstPK = new GmstStudentMstPK();
			gmstStudentMstPK.setUnumStudentId(studentMasterBean.getUnumStudentId());
			gmstStudentMstPK.setUnumIsvalid(2);
			studentRepository.deleteById(gmstStudentMstPK);
		}
		studentMasterBean.setUnumIsvalid(1);
		GmstStudentMst gmstStudentMst = BeanUtils.copyProperties(studentMasterBean, GmstStudentMst.class);
		dateFormater(studentMasterBean, gmstStudentMst);
		studentRepository.save(gmstStudentMst);
		return ServiceResponse.successObject(studentMasterBean);
	}

	private void dateFormater(StudentMasterBean studentMasterBean, GmstStudentMst gmstStudentMst) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(studentMasterBean.getUdtEntryDate() != null)
			gmstStudentMst.setUdtStuDob(df.parse(studentMasterBean.getUdtStuDob()));
		if(studentMasterBean.getUdtEntryDate() != null)
			gmstStudentMst.setUdtEffFrom(df.parse(studentMasterBean.getUdtEffFrom()));
		if(studentMasterBean.getUdtEntryDate() != null)
			gmstStudentMst.setUdtEntryDate(df.parse(studentMasterBean.getUdtEntryDate()));
	}
	
	@Transactional
	public ServiceResponse saveUpdateDraft(@Valid StudentMasterBean studentMasterBean) throws Exception {
		
		
        
		if(studentMasterBean.getUnumStudentId() == 1L) { //save
			studentMasterBean.setUnumStudentId(studentRepository.getNextId());
		}
		else if(studentMasterBean.getUnumIsvalid() ==1) {
			return ServiceResponse.successObject(studentMasterBean);
		}
		else { //update
			studentRepository.delete(BeanUtils.copyProperties(studentMasterBean, GmstStudentMst.class));
		}
		
        Date d1 = new Date();

        studentMasterBean.setUnumUnivId(RequestUtility.getUniversityId());
        studentMasterBean.setUnumEntryUid(RequestUtility.getUserId());
        
		GmstStudentMst gmstStudentMst = BeanUtils.copyProperties(studentMasterBean, GmstStudentMst.class);
		dateFormater(studentMasterBean, gmstStudentMst);
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
	
	public ServiceResponse getStudentDetailsDraft(String studentEnrollmentNo ) throws Exception {
		GmstStudentMst gmstStudentMst = studentRepository.findByUstrEnrollmentNoIgnoreCaseAndUnumUnivIdAndUnumIsvalid(studentEnrollmentNo,RequestUtility.getUniversityId(),2);
		StudentMasterBean studentMasterBean = null ;
		if(gmstStudentMst!=null) {
			studentMasterBean =BeanUtils.copyProperties(gmstStudentMst, StudentMasterBean.class);
		}
		return ServiceResponse.successObject(studentMasterBean);
	}
}
