package com.crud_simple.crud.services;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileService
 */
@Service
public class FileService {
    private static final String uploadImageDir = System.getProperty("user.dir")
            + "/src/main/resources/static/uploads/images/";
    private static final String uploadBerkasDir = System.getProperty("user.dir")
            + "/src/main/resources/static/uploads/berkas/";

    public static String uploadImage(MultipartFile multipartFile) throws IOException {
        Integer random = ThreadLocalRandom.current().nextInt();
        String newName = String.valueOf(random) + ".png";
        File dest = new File(uploadImageDir + newName);
        multipartFile.transferTo(dest);

        return newName;
    }

    public static String uploadBerkas(MultipartFile multipartFile) throws IOException {
        Integer random = ThreadLocalRandom.current().nextInt();
        String newName = String.valueOf(random) + ".png";
        File dest = new File(uploadBerkasDir + newName);
        multipartFile.transferTo(dest);

        return newName;
    }
}
