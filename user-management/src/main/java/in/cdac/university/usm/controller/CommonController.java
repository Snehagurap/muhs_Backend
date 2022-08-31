package in.cdac.university.usm.controller;

import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/usm/global")
public class CommonController {

    @RequestMapping("columns/{className}")
    public ResponseEntity<?> getColumns(@PathVariable("className") String className) throws ClassNotFoundException {
        String decodedClassName = new String(Base64.getDecoder().decode(className.getBytes()));
        System.out.println("CLass Name " + className);
        System.out.println("Decoded CLass Name " + decodedClassName);
        Class<?> clazz = Class.forName(decodedClassName);
        return ResponseHandler.generateOkResponse(ListPageUtility.getColumns(clazz));
    }
}
