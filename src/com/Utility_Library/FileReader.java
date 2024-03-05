package com.Utility_Library;

import io.github.pixee.security.BoundedLineReader;
import java.io.*;
import java.util.*;

public class FileReader {
    private ErrorLogger errorLogger;
    public List<String> fileLines;
    public FileReader()
    {
        errorLogger = ErrorLogger.getInstance();
    }

    public List<String> readFile(String file)
    {
        FileInputStream fileInputStream;
        fileLines = new ArrayList<String>();
        try
        {
            fileInputStream = new FileInputStream(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(fileInputStream));
            String aLine;

            while ((aLine = BoundedLineReader.readLine(in, 5_000_000)) != null) {
                fileLines.add(aLine);
            }
        }
        catch (IOException ex)
        {
            errorLogger.logException(ex);
        }
        return fileLines;
    }

}
