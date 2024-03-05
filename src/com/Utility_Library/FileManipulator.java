package com.Utility_Library;

import io.github.pixee.security.BoundedLineReader;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManipulator {

    private ErrorLogger errorLogger;

    public FileManipulator() {
        errorLogger = ErrorLogger.getInstance();
    }
    public boolean moveFiles(String locationPath, String destinationPath, String searchTerm, String newName) {
        File locationDirectory = new File(locationPath);
        if (locationDirectory.isDirectory()) {
            File files[] = locationDirectory.listFiles();
            String smartBacklogPath;
            for (File file : files) {
                if (file.getName().toUpperCase().contains(searchTerm)) {
                    smartBacklogPath = file.getPath();
                    try {
                        Files.move(Paths.get(smartBacklogPath), Paths.get(destinationPath + newName), StandardCopyOption.REPLACE_EXISTING);
                        return true;
                    } catch (IOException ex) {
                        errorLogger.logException(ex);
                    }
                    break;
                }
            }
        }
        return false;
    }

    public boolean copyFile_SkipLines(String locationPath, String oldFile, String newFile, int linesToSkip) {
        if (!new File(locationPath + oldFile).isFile())
            return false;
        FileWriter fstream = null;
        BufferedWriter out = null;
        FileInputStream fileInputStream;
        try {
            fstream = new FileWriter(locationPath + newFile);
            out = new BufferedWriter(fstream);
        } catch (IOException ex) {
            errorLogger.logException(ex);
            return false;
        }

        try {
            fileInputStream = new FileInputStream(locationPath + oldFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fileInputStream));

            String aLine;

            if (linesToSkip > 0) {
                for (int counter = 0; counter < linesToSkip; counter++) {
                    BoundedLineReader.readLine(in, 5_000_000);
                }
            }
            while ((aLine = BoundedLineReader.readLine(in, 5_000_000)) != null) {
                out.write(aLine);
                out.newLine();
            }
            in.close();
            out.close();

        } catch (IOException ex) {
            errorLogger.logException(ex);
            return false;
        }
        return true;
    }

    public boolean mergeFiles(File[] files, String locationPath, String newFile) {
        for (File f : files) {
            if (!f.isFile())
                return false;
        }
        FileWriter fstream = null;
        BufferedWriter out = null;
        FileInputStream fileInputStream;
        try {
            fstream = new FileWriter(locationPath + newFile);
            out = new BufferedWriter(fstream);
        } catch (IOException ex) {
            errorLogger.logException(ex);
            return false;
        }


        for (File file : files) {
            try {
                fileInputStream = new FileInputStream(file);
                BufferedReader in = new BufferedReader(new InputStreamReader(fileInputStream));
                String aLine;


                while ((aLine = BoundedLineReader.readLine(in, 5_000_000)) != null) {
                    out.write(aLine);
                    out.newLine();
                }
                in.close();
            } catch (IOException ex) {
                errorLogger.logException(ex);
                return false;
            }
        }
        try {
            out.close();
        } catch (IOException ex) {
            errorLogger.logException(ex);
            return false;
        }
        return true;
    }

    public boolean reNameFile(String locationPath, String searchTerm, String newName) {
        File locationDirectory = new File(locationPath);
        if (locationDirectory.isDirectory()) {
            File files[] = locationDirectory.listFiles();
            String smartBacklogPath;
            for (File file : files) {
                if (file.getName().toUpperCase().contains(searchTerm.toUpperCase())) {
                    file.renameTo(new File(locationPath + newName));
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void deleteAllFiles(String locationPath) {
        File locationDirectory = new File(locationPath);
        if (locationDirectory.isDirectory()) {
            File files[] = locationDirectory.listFiles();
            for (File file : files) {
                try {
                    file.delete();
                } catch (Exception ex) {
                    errorLogger.logException(ex);
                }
            }
        }
    }
    public void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception ex) {
            errorLogger.logException(ex);
        }
    }
}
