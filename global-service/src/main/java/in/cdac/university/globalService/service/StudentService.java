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
		 Date d1 = new Date();
		if(studentMasterBean.getUdtEntryDate() != null && (!studentMasterBean.getUdtEntryDate().isEmpty()))
			gmstStudentMst.setUdtStuDob(df.parse(studentMasterBean.getUdtStuDob()));
		if(studentMasterBean.getUdtEntryDate() != null  && (!studentMasterBean.getUdtEffFrom().isEmpty()) )
			gmstStudentMst.setUdtEffFrom(df.parse(studentMasterBean.getUdtEffFrom()));
		else {
			gmstStudentMst.setUdtEffFrom(d1);
		}
		if(studentMasterBean.getUdtEntryDate() != null  && (!studentMasterBean.getUdtEntryDate().isEmpty()))
			gmstStudentMst.setUdtEntryDate(df.parse(studentMasterBean.getUdtEntryDate()));
		else {
			gmstStudentMst.setUdtEntryDate(d1);
		}
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

	public ServiceResponse getStudentDetailsByScheme(int schemeNo) {
		
		
		
		
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceResponse getAllStudentDetails() throws Exception {
		List<GmstStudentMst> gmstStudentMst = studentRepository.findByUnumUnivIdAndUnumIsvalid(RequestUtility.getUniversityId(),1);
		List<StudentMasterBean> studentMasterBean = null ;
		if(gmstStudentMst!=null) {
			studentMasterBean =BeanUtils.copyListProperties(gmstStudentMst, StudentMasterBean.class);
		}
		return ServiceResponse.successObject(studentMasterBean);
	}
}
