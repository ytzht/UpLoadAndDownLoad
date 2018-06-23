package vip.mae.uploadanddownload;

public interface Apis {

    String Base = "http://192.168.1.171:8080/FileUpload/fileUpload/";

    String UpLoad = Base + "addFileTxt";


    String DownLoad = Base + "getFileList";


    String FileList = Base + "getFileList";
}
