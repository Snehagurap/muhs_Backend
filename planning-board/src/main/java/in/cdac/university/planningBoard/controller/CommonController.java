package in.cdac.university.planningBoard.controller;

import in.cdac.university.planningBoard.util.ListPageUtility;
import in.cdac.university.planningBoard.util.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/pb")
public class CommonController {

    @GetMapping("columns/{className}")
    public ResponseEntity<?> getColumns(@PathVariable("className") String className) throws ClassNotFoundException {
        String decodedClassName = new String(Base64.getDecoder().decode(className.getBytes()));
        Class<?> clazz = Class.forName(decodedClassName);
        return ResponseHandler.generateOkResponse(ListPageUtility.getColumns(clazz));
    }
}
