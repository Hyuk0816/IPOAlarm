package com.sideprj.ipoAlarm.util.S3.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.sideprj.ipoAlarm.util.S3.service.S3Service;
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

    private final String fileName = "ipo_data.csv";

    @Override
    public void downloadFile() throws FileNotFoundException {
        S3ObjectInputStream objectContent = s3Client.getObject(downBucket, fileName).getObjectContent();
        saveFile(objectContent, fileName);
    }

    @Override
    public void saveFile(InputStream inputStream, String fileName) throws FileNotFoundException {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + File.separator + fileName;
        File file = new File(filePath);

        try(FileOutputStream outputStream = new FileOutputStream(file)){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while Saving file to folder",e);
        }finally {
            try{
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
