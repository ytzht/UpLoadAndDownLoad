package vip.mae.uploadanddownload;

public interface Apis {

    String Base = "http://192.168.2.154:8080/FileUpload/fileUpload/";

    String UpLoad = Base + "addFileTxt";


    String DownLoad = Base + "downaa?filename=";


    String FileList = Base + "getFileList";
}
