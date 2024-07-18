package com.sideprj.ipoAlarm.util.S3.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface S3Service {
    void downloadFile() throws FileNotFoundException;

    void saveFile(InputStream inputStream, String fileName) throws FileNotFoundException;

}
