package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.FtpService;
import in.cdac.university.globalService.util.FtpUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/global")
@RestController
public class FileUploadController {

    @Autowired
    private FtpService ftpService;

    @PostMapping("file/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folder) {
        FtpUtility.FTP_DIRECTORY fileDirectory = FtpUtility.FTP_DIRECTORY.COMMITTEE;
//        try {
//            FtpUtility.FTP_DIRECTORY.valueOf(folder);
//        } catch (IllegalArgumentException iae) {
//            return ResponseHandler.generateErrorResponse("Folder with name " + folder + " not found.");
//        }

        return ResponseHandler.generateResponse(
                ftpService.uploadFile(file, fileDirectory)
        );
    }
}
