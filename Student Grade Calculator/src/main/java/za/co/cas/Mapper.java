package za.co.cas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Mapper {
    protected File FILE;
    protected ObjectMapper mapper;

    Mapper(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            FILE = file;
            mapper = new ObjectMapper();
        }
        if (!FILE.exists())
            try {
                Files.createFile(Path.of(FILE.getAbsolutePath()));
            } catch (IOException ignored) {
                throw new RuntimeException(ignored);
            }
    }

    public String map(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String pretty(Object obj) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Object obj) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(FILE, obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}