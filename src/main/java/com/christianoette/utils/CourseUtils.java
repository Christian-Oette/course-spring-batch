package com.christianoette.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

public class CourseUtils {

    public static FileSystemResource getFileResource(String path) {
        try {
            return new FileSystemResource(ResourceUtils.getFile(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
