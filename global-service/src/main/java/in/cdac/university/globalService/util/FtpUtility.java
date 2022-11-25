package in.cdac.university.globalService.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
@RefreshScope
@Slf4j
public class FtpUtility {

    public enum FTP_DIRECTORY {
        COMMITTEE ("com"),
        NOTIFICATION ("notification"),
        REGISTRATION ("registration"),
        APPLICANT ("applicant");

        public final String folderName;

        FTP_DIRECTORY(String folderName) {
            this.folderName = folderName;
        }

        public static FTP_DIRECTORY getByValue(String value) {
            return Arrays.stream(FTP_DIRECTORY.values())
                    .filter(ftpDirectory -> ftpDirectory.folderName.equals(value))
                    .findFirst()
                    .orElse(null);
        }
    }

    @Value("${ftp.url}")
    private String ftpUrl;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    private FTPClient connectToFtp() {
        FTPClient ftpClient;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpUrl);
            if (ftpClient.login(ftpUsername, ftpPassword)) {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                return ftpClient;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean changeOrCreateDirectory(FTPClient ftpClient, String directoryName) throws IOException {
        boolean isDirectoryExists = ftpClient.changeWorkingDirectory(directoryName);
        if (!isDirectoryExists) {
            boolean result = ftpClient.makeDirectory(directoryName);
            if (result)
                return ftpClient.changeWorkingDirectory(directoryName);
            else
                return false;
        }
        return  true;
    }

    public boolean uploadFileToTempDirectory(FTP_DIRECTORY fileDirectory, MultipartFile file, String fileNameToSave) {
        FTPClient ftpClient = connectToFtp();
        if (ftpClient == null) {
            log.error("Unable to establish a connection to FTP Server");
            return false;
        }
        try {
            boolean result = changeOrCreateDirectory(ftpClient, "temp");
            if (!result) {
                log.error("Unable to create temporary directory");
                return false;
            }

            result = changeOrCreateDirectory(ftpClient, fileDirectory.folderName);
            if (!result) {
                log.error("Unable to create directory");
                return false;
            }
            System.out.println("PWD " + ftpClient.printWorkingDirectory());
            return ftpClient.storeFile(fileNameToSave, file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean moveFileFromTempToFinalDirectory(String fileName) {
        FTPClient ftpClient = connectToFtp();
        if (ftpClient == null) {
            log.error("Unable to establish a connection to FTP Server");
            return false;
        }
        try {
            log.debug("Current Directory {}", ftpClient.printWorkingDirectory());
            String[] directories = fileName.split("/");
            for (int i=0;i<directories.length - 1;++i) {
                log.debug("Changing directory {}", directories[i]);
                boolean result = changeOrCreateDirectory(ftpClient, directories[i]);
                if (!result)
                    return false;
            }
            for (int i=0;i<directories.length - 1;++i) {
                log.debug("Current Directory {}", ftpClient.printWorkingDirectory());
                boolean result = ftpClient.changeToParentDirectory();
                if (!result)
                    return false;
            }
            log.debug("Current Directory {}", ftpClient.printWorkingDirectory());
            log.debug("Moving File: temp/{}, to {}", fileName, fileName);
            return ftpClient.rename("temp/" + fileName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteFileFromTempDirectory(String fileName) {
        FTPClient ftpClient = connectToFtp();
        if (ftpClient == null) {
            log.error("Unable to establish a connection to FTP Server");
            return false;
        }
        try {
            boolean result = changeOrCreateDirectory(ftpClient, "temp");
            if (!result)
                return false;
            return ftpClient.deleteFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public byte[] downloadFile(String fileName) {
        FTPClient ftpClient = connectToFtp();
        if (ftpClient == null) {
            log.error("Unable to establish a connection to FTP Server");
            return null;
        }
        try {
            InputStream inputStream = ftpClient.retrieveFileStream(fileName);
            if (inputStream == null) {
                log.info("File: {} not found in Main directory check in Temp directory", fileName);
                fileName = "temp/" + fileName;
                inputStream = ftpClient.retrieveFileStream(fileName);
            }
            byte[] fileBytes = inputStream.readAllBytes();
            ftpClient.completePendingCommand();
            inputStream.close();
            return fileBytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isFileExists(String fileName) {
        FTPClient ftpClient = connectToFtp();
        if (ftpClient == null) {
            log.error("Unable to establish a connection to FTP Server");
            return false;
        }
        try {
            log.debug("Checking if file [{}] exists", fileName);
            log.debug("PWD: {}", ftpClient.printWorkingDirectory());
            FTPFile[] ftpFile = ftpClient.listFiles(fileName);
            return ftpFile.length > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
