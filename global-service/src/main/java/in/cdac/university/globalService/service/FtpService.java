package in.cdac.university.globalService.service;

import com.google.common.hash.Hashing;
import in.cdac.university.globalService.bean.FtpBean;
import in.cdac.university.globalService.util.FtpUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    private final Pattern fileNameRegex = Pattern.compile("^[A-Za-z0-9-_\\s]+[.][A-Za-z]{3,4}$");

    public ServiceResponse uploadFile(MultipartFile file, FtpUtility.FTP_DIRECTORY ftpDirectory, String clientFileToken, String fileNameClientToken) throws IOException {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            return ServiceResponse.errorResponse("No file found to upload");
        }

        String fileNameServerToken = Hashing.sha256().hashString(file.getOriginalFilename(), StandardCharsets.UTF_8).toString();
        if (fileNameClientToken != null && !fileNameClientToken.equals(fileNameServerToken)) {
            log.error("File Name: {}, Client Token: {}, Server Token: {}", file.getOriginalFilename(), fileNameClientToken, fileNameServerToken);
            return ServiceResponse.errorResponse("Form data tampered");
        }

        Matcher matcher = fileNameRegex.matcher(file.getOriginalFilename());
        if (!matcher.matches()) {
            log.error("File Name: {}", file.getOriginalFilename());
            return ServiceResponse.errorResponse("Invalid file name. File name can be alphanumeric with space, underscore and dashes only.");
        }

        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        // Check for file extension
        Optional<String> fileExtensionOptional = allowedExtensions.stream()
                .filter(fileExtension::equalsIgnoreCase)
                .findFirst();
        if (fileExtensionOptional.isEmpty()) {
            log.error("File extension: {}", fileExtension);
            return ServiceResponse.errorResponse("Invalid file extension. Allowed extension: " + allowedExtensions);
        }

        // Check for file types
        String contentType = file.getContentType();
        Optional<String> contentTypeOptional = allowedContentTypes.stream()
                .filter(allowedContentType -> allowedContentType.equals(contentType))
                .findFirst();

        if (contentTypeOptional.isEmpty()) {
            log.error("Content Type: {}", contentType);
            return ServiceResponse.errorResponse("Invalid file type. Allowed file types are png, jpeg, jpg, pdf, doc, docx");
        }

        // Check for file content
        Metadata metadata = new Metadata();
        metadata.add(TikaCoreProperties.RESOURCE_NAME_KEY, file.getOriginalFilename());

        Tika tika = new Tika();
        String detectedType = tika.detect(file.getInputStream(), metadata);
        if (!detectedType.equals(contentType)) {
            log.error("Detected file type: {}", detectedType);
            log.error("File Content type: {}", contentType);
            return ServiceResponse.errorResponse("Invalid file.");
        }
        String fileNameWithoutExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        String fileNameToSave = System.currentTimeMillis() + "_" + fileNameWithoutExtension;
        if (fileNameToSave.length() > 45)
            fileNameToSave = fileNameToSave.substring(0, 45);
        fileNameToSave += "." + fileExtension;

        // Read File data
        String fileContent = Base64.getEncoder().encodeToString(file.getBytes());
        String serverFileToken = Hashing.sha256().hashString(fileContent, StandardCharsets.UTF_8).toString();
        log.debug("Server file token: {}", serverFileToken);
        log.debug("Client file token: {}", clientFileToken);
        if (clientFileToken != null && !clientFileToken.equals(serverFileToken)) {
            log.error("File content: {}", fileContent);
            log.error("Server file token: {}", serverFileToken);
            log.error("Client file token: {}", clientFileToken);
            return ServiceResponse.errorResponse("Form data tampered");
        }

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
        // Check if file is already present in final directory
        if (ftpUtility.isFileExists(fileName)) {
            status = 1;
            message = "File already exists in directory";
        } else {
            if (ftpUtility.moveFileFromTempToFinalDirectory(fileName)) {
                status = 1;
                message = "File moved successfully";
            } else {
                status = 0;
                message = "Unable to move file";
            }
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

        ftpUtility.deleteFileFromTempDirectory(fileName);
        int status = 1;
        String message = "File deleted successfully";

        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ResponseEntity<?> downloadFile(String fileName) {
        byte[] fileBytes = ftpUtility.downloadFile(fileName);
        return ResponseHandler.generateFileResponse(fileBytes, fileName);
    }

    public ServiceResponse isFileExists(String fileName) {
        if (fileName == null) {
            return ServiceResponse.errorResponse("No file found to check");
        }
        int status;
        String message;
        if (ftpUtility.isFileExists(fileName)) {
            status = 1;
            message = "File exists";
        } else {
            status = 0;
            message = "File does not exists";
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
