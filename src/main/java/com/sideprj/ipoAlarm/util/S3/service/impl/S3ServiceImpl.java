package com.sideprj.ipoAlarm.util.S3.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.sideprj.ipoAlarm.util.S3.service.S3Service;
import com.sideprj.ipoAlarm.util.S3.constants.SaveFileConstants;
import com.sideprj.ipoAlarm.util.exception.S3ResourceNotFoundException;
import com.sideprj.ipoAlarm.util.exception.SaveFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String downBucket;
    public static final String fileName = "ipo_data.csv";
    public static final String saveDir = System.getProperty("user.dir");

    @Override
    public void downloadFile() {
        try{
            S3ObjectInputStream objectContent = s3Client.getObject(downBucket, fileName).getObjectContent();
            saveFile(objectContent, fileName);
        }catch (S3ResourceNotFoundException e){
            throw new S3ResourceNotFoundException(downBucket, fileName);
        }
    }

    @Override
    public void saveFile(InputStream inputStream, String fileName) {
        String filePath = saveDir + File.separator + fileName;
        File file = new File(filePath);

        try(FileOutputStream outputStream = new FileOutputStream(file)){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new SaveFileException(SaveFileConstants.saveFIleError,e);
        }finally {
            try{
                inputStream.close();
            }catch (IOException e){
                log.info(e.getMessage());
            }
        }
    }
}
