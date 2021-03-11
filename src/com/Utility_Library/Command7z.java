package com.Utility_Library;

import java.io.File;
import java.io.IOException;

public class Command7z {
    private ErrorLogger errorLogger;

    public Command7z() {
        errorLogger = ErrorLogger.getInstance();
    }

    public void UnCompressFile(String filePath, String saveToPath, String password, String newName) {
        if (!password.isEmpty())
            password = "-p" + password;
        try {
            ProcessBuilder processBuilder;
            if(!password.isBlank()) {
                processBuilder = new ProcessBuilder(
                        "7z",
                        "e",
                        filePath,
                        password,
                        "-so"
                );
            }
            else {
                processBuilder = new ProcessBuilder(
                        "7z",
                        "e",
                        filePath);
            }
            processBuilder.directory(new File(saveToPath));

            if(!newName.isEmpty()) {
                processBuilder.redirectOutput(new File(saveToPath + newName));
            }
            Process p = processBuilder.start();
            System.out.println("Started UnCompressing File");
            try {
                p.waitFor();
                System.out.println("UnCompressed :");
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            errorLogger.logException(ex);
        }
    }

    public void CompressFile(String filePath, String savePath, String fileName, String archiveFormat, int compressionLevel) {
        String compressionParameter = "-mx";
        if (compressionLevel > 0 && compressionLevel < 10) {
            compressionParameter += Integer.toString(compressionLevel);
        } else compressionParameter += "1";

        if (archiveFormat != "7z" && archiveFormat == "zip" && archiveFormat == "tar") {
            archiveFormat = "7z";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("-t" + archiveFormat);
        try {
            String newFileName = savePath + fileName + "." + archiveFormat;
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "7z",
                    "a",
                    builder.toString(),
                    compressionParameter,
                    newFileName,
                    "*",
                    "-x!*.db"
            );
            processBuilder.directory(new File(filePath));
            Process p = processBuilder.start();
            System.out.println("Started Compressing File");
            try {
                p.waitFor();
                System.out.println("Compressed");
            } catch (InterruptedException ex) {
                System.out.println("Unable to compress file");
                System.out.println(ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            errorLogger.logException(ex);
        }
    }
}
