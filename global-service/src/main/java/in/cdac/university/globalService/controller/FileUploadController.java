package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.FtpBean;
import in.cdac.university.globalService.service.FtpService;
import in.cdac.university.globalService.util.FtpUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/global")
@RestController
public class FileUploadController {

    @Autowired
    private FtpService ftpService;

    @PostMapping("file/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folder) throws IOException {
        FtpUtility.FTP_DIRECTORY fileDirectory = FtpUtility.FTP_DIRECTORY.getByValue(folder);

        if (fileDirectory == null)
            return ResponseHandler.generateErrorResponse("Folder with name " + folder + " not found.");

        return ResponseHandler.generateResponse(
                ftpService.uploadFile(file, fileDirectory)
        );
    }

    @PostMapping("file/delete")
    public ResponseEntity<?> deleteFile(@Valid @RequestBody FtpBean ftpBean) {
        return ResponseHandler.generateResponse(
                ftpService.deleteFile(ftpBean.getFileName())
        );
    }

    @PostMapping("file/moveToFinal")
    public ResponseEntity<?> moveFile(@Valid @RequestBody FtpBean ftpBean) {
        return ResponseHandler.generateResponse(
                ftpService.moveFileFromTempToFinalDirectory(ftpBean.getFileName())
        );
    }

    @PostMapping("file/download")
    public ResponseEntity<?> downloadFile(@Valid @RequestBody FtpBean ftpBean) {
        return ftpService.downloadFile(ftpBean.getFileName());
    }

    @PostMapping("file/exists")
    public ResponseEntity<?> isFileExists(@Valid @RequestBody FtpBean ftpBean) {
        return ResponseHandler.generateResponse(
                ftpService.isFileExists(ftpBean.getFileName())
        );
    }
}
