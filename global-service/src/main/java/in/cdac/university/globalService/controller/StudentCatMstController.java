package in.cdac.university.globalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import in.cdac.university.globalService.service.StudentCatMstService;

@RestController
@RequestMapping("/global/StudentCat")
public class StudentCatMstController {

	@Autowired
	private StudentCatMstService stuCatMstService;
}
