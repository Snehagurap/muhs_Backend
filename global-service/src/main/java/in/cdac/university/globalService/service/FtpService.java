package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.FtpBean;
import in.cdac.university.globalService.util.FtpUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class FtpService {

    @Autowired
    private FtpUtility ftpUtility;

    private static final List<String> allowedContentTypes = List.of(
            "image/jpeg", "image/png", "image/jpeg",
            "application/pdf", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",      // docx
            "application/vnd.oasis.opendocument.text"                                       // odt
    );

    private static final List<String> allowedExtensions = List.of("png", "jpg", "jpeg", "pdf", "doc", "docx");

    private final Pattern fileNameRegex = Pattern.compile("^[A-Za-z-_\\d\\s]+[.][A-Za-z]{3,4}$");

    public ServiceResponse uploadFile(MultipartFile file, FtpUtility.FTP_DIRECTORY ftpDirectory) throws IOException {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            return ServiceResponse.errorResponse("No file found to upload");
        }

        log.debug("File Name: {}", file.getOriginalFilename());
        Matcher matcher = fileNameRegex.matcher(file.getOriginalFilename());
        if (!matcher.matches()) {
            return ServiceResponse.errorResponse("Invalid file name. File name can be alphanumeric with space, underscore and dashes only.");
        }

        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        log.debug("File extension: {}", fileExtension);
        // Check for file extension
        Optional<String> fileExtensionOptional = allowedExtensions.stream()
                .filter(fileExtension::equalsIgnoreCase)
                .findFirst();
        if (fileExtensionOptional.isEmpty()) {
            return ServiceResponse.errorResponse("Invalid file extension. Allowed extension: " + allowedExtensions);
        }

        // Check for file types
        String contentType = file.getContentType();
        log.debug("Content Type: {}", contentType);
        Optional<String> contentTypeOptional = allowedContentTypes.stream()
                .filter(allowedContentType -> allowedContentType.equals(contentType))
                .findFirst();

        if (contentTypeOptional.isEmpty()) {
            return ServiceResponse.errorResponse("Invalid file type. Allowed file types are png, jpeg, jpg, pdf, doc, docx");
        }

        // Check for file content
        Tika tika = new Tika();
        String detectedType = tika.detect(file.getBytes());
        if (!detectedType.equals(contentType)) {
            return ServiceResponse.errorResponse("Invalid file.");
        }

        String fileNameToSave = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        if (fileNameToSave.length() > 50)
            fileNameToSave = fileNameToSave.substring(0, 50);

        boolean result = ftpUtility.uploadFileToTempDirectory(ftpDirectory, file, fileNameToSave);
        if (result) {
            FtpBean ftpBean = new FtpBean(ftpDirectory.folderName + "/" + fileNameToSave);
            return ServiceResponse.builder()
                    .status(1)
                    .responseObject(ftpBean)
                    .build();
        } else {
            return ServiceResponse.errorResponse("Unable to upload file");
        }
    }

    public ServiceResponse moveFileFromTempToFinalDirectory(String fileName) {
        if (fileName == null) {
            return ServiceResponse.errorResponse("No file found to move");
        }
        int status;
        String message;
        if (ftpUtility.moveFileFromTempToFinalDirectory(fileName)) {
            status = 1;
            message = "File moved successfully";
        } else {
            status = 0;
            message = "Unable to move file";
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse deleteFile(String fileName) {
        if (fileName == null) {
            return ServiceResponse.errorResponse("No file found to delete");
        }
        int status;
        String message;
        if (ftpUtility.deleteFileFromTempDirectory(fileName)) {
            status = 1;
            message = "File deleted successfully";
        } else {
            status = 0;
            message = "Unable to delete file";
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ResponseEntity<?> downloadFile(String fileName) {
        byte[] fileBytes = ftpUtility.downloadFile(fileName);
        if (fileBytes == null) {
            return null;
        }
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileBytes));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentLength(fileBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
