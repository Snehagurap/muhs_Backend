package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.FtpBean;
import in.cdac.university.globalService.util.FtpUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RefreshScope
public class FtpService {

    @Value("${ftp.url}")
    private String ftpUrl;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    public ServiceResponse uploadFile(MultipartFile file, FtpUtility.FTP_DIRECTORY ftpDirectory) {
        if (file == null || file.getOriginalFilename() == null) {
            return ServiceResponse.errorResponse("No file found to upload");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();
        log.info("FIle Name to upload " + fileName);
        log.info("FIle size " + size);

        String fileNameToSave = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        if (fileNameToSave.length() > 50)
            fileNameToSave = fileNameToSave.substring(0, 50);

        FtpBean ftpBean = new FtpBean(ftpDirectory.folderName + "/" + fileNameToSave);
        return ServiceResponse.builder()
                .status(1)
                .responseObject(ftpBean)
                .build();

//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        long size = file.getSize();
//        log.info("FIle Name to upload " + fileName);
//        log.info("FIle size " + size);
//
//        FTPClient ftpClient = null;
//        try {
//            ftpClient = new FTPClient();
//            ftpClient.connect(ftpUrl);
//            if (ftpClient.login(ftpUsername, ftpPassword)) {
//                ftpClient.enterLocalPassiveMode();
//                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//                boolean isDirectoryExists = ftpClient.changeWorkingDirectory(ftpDirectory.folderName);
//                if (!isDirectoryExists)
//                    isDirectoryExists = ftpClient.makeDirectory(ftpDirectory.folderName);
//
//                if (isDirectoryExists) {
//                    String fileNameToSave = System.currentTimeMillis() + file.getOriginalFilename();
//                    if (fileNameToSave.length() > 50)
//                        fileNameToSave = fileNameToSave.substring(0, 50);
//                    boolean result = ftpClient.storeFile(fileNameToSave, file.getInputStream());
//                    if (result) {
//                        FtpBean ftpBean = new FtpBean(ftpDirectory.folderName + "/" + fileNameToSave);
//                        return ServiceResponse.builder()
//                                .status(1)
//                                .responseObject(ftpBean)
//                                .build();
//                    }
//                } else {
//                    return ServiceResponse.errorResponse("Unable to create new Directory.");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (ftpClient != null) {
//                try {
//                    ftpClient.logout();
//                    ftpClient.disconnect();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return ServiceResponse.errorResponse("Unable to upload file.");
    }
}
