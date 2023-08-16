package com.shop.fruitfruit.web.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FireBaseService {

    @Value(value = "${firebase.bucket}")
    private String firebaseBucket;

    public String uploadFiles(MultipartFile file, String path, String fileName) throws IOException {

        if (file.isEmpty()) {
            log.error("File is Empty");
            return "File is Empty";
        }

        StringBuilder sb = new StringBuilder();
        if (!path.equals("")) {
            sb.append(path + "/");
        }

        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);

        String fileSeqName = getUniqueFileName(bucket, sb.toString(), fileName, 0);

        sb.append(fileSeqName);

        InputStream content = new ByteArrayInputStream(file.getBytes());
        Blob blob = bucket.create(sb.toString(), content, file.getContentType());

        return blob.getMediaLink();
    }

    private String getUniqueFileName(Bucket bucket, String path, String fileName, int seq) {
        StringBuilder tempFileName = new StringBuilder(fileName);

        if (seq > 0) {
            tempFileName.insert(tempFileName.lastIndexOf("."), "(" + seq + ")");
        }

        Blob blob = bucket.get(path + tempFileName.toString());
        if (blob != null) {
            return getUniqueFileName(bucket, path, fileName, seq + 1);
        } else {
            return tempFileName.toString();
        }
    }
}