package in.cdac.university.globalService.util;

public class FtpUtility {

    public enum FTP_DIRECTORY {
        COMMITTEE ("com"),
        ;

        public final String folderName;

        FTP_DIRECTORY(String folderName) {
            this.folderName = folderName;
        }
    }
}
