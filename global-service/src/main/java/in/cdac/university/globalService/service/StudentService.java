package in.cdac.university.globalService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cdac.university.globalService.repository.StudentRepository;
import in.cdac.university.globalService.util.Language;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private Language language;
}
