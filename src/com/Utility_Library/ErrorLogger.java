package com.Utility_Library;

import javax.mail.MessagingException;
import java.io.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger {
    private static ErrorLogger single_instance = null;
    private File loggerFile;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String loggerFileName;
    private String loggerPath;

    protected ErrorLogger(String projectPath) {
        init(projectPath);
    }

    protected ErrorLogger() {

    }

    public static ErrorLogger getInstance(String projectPath) {
        if (single_instance == null)
            single_instance = new ErrorLogger(projectPath);
        return single_instance;
    }

    public static ErrorLogger getInstance() {
        if (single_instance == null)
            single_instance = new ErrorLogger();
        return single_instance;
    }

    private void init(String projectPath) {
        try {
            new File(projectPath).mkdir();
            new File(projectPath + File.separator + "Logs").mkdir();
            loggerPath = projectPath + File.separator + "Logs";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setPath(String loggerPath) {
        this.loggerPath = loggerPath;
    }

    public void setFileLogger(String loggerFileName) {
        this.loggerFileName = loggerPath + File.separator + loggerFileName;
        loggerFile = new File(this.loggerFileName);
    }

    public void logDate() {
        addTextToLogFile(dateTimeFormatter.format(LocalDateTime.now()), true);
    }

    public void logException(Exception ex) {
        addTextToLogFile("\n**********************************\n", true);
        logDate();
        addTextToLogFile("Type: Exception", true);
        addTextToLogFile("Cause: " + ex.getCause(), true);
        addTextToLogFile("Message: " + ex.getMessage(), true);
        addTextToLogFile("Stack Trace: " + ex.getStackTrace().toString(), true);
        addTextToLogFile("\n**********************************\n", true);

    }
    public void logException(SQLException ex) {
        addTextToLogFile("\n**********************************\n", true);
        logDate();
        addTextToLogFile("Type: Exception", true);
        addTextToLogFile("Cause: " + ex.getCause(), true);
        addTextToLogFile("Message: " + ex.getMessage(), true);
        addTextToLogFile("Stack Trace: " + ex.getStackTrace().toString(), true);
        addTextToLogFile("\n**********************************\n", true);

    }

    public void logException(InterruptedException ex) {
        addTextToLogFile("\n**********************************\n", true);
        logDate();
        addTextToLogFile("Type: InterruptedException", true);
        addTextToLogFile("Cause: " + ex.getCause(), true);
        addTextToLogFile("Message: " + ex.getMessage(), true);
        addTextToLogFile("Stack Trace: " + ex.getStackTrace().toString(), true);
        addTextToLogFile("\n**********************************\n", true);

    }

    public void logException(FileNotFoundException ex) {
        addTextToLogFile("\n**********************************\n", true);
        logDate();
        addTextToLogFile("Type: FileNotFoundException", true);
        addTextToLogFile("Cause: " + ex.getCause(), true);
        addTextToLogFile("Message: " + ex.getMessage(), true);
        addTextToLogFile("Stack Trace: " + ex.getStackTrace().toString(), true);
        addTextToLogFile("\n**********************************\n", true);
    }

    public void logException(IOException ex) {
        addTextToLogFile("\n**********************************", true);
        logDate();
        addTextToLogFile("Type: IOException", true);
        addTextToLogFile("Cause: " + ex.getCause(), true);
        addTextToLogFile("Message: " + ex.getMessage(), true);
        addTextToLogFile("Stack Trace: " + ex.getStackTrace().toString(), true);
        addTextToLogFile("\n**********************************", true);
    }

    public void logException(MessagingException ex) {
        addTextToLogFile("\n**********************************\n", true);
        logDate();
        addTextToLogFile("Type: MessagingException", true);
        addTextToLogFile("Cause: " + ex.getCause(), true);
        addTextToLogFile("Message: " + ex.getMessage(), true);
        addTextToLogFile("Stack Trace: " + ex.getStackTrace().toString(), true);
        addTextToLogFile("\n**********************************\n", true);
    }

    public void addTextToLogFile(String logMessage, boolean append) {
        if ((loggerFile).isFile()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(loggerFile, append));
                writer.write(logMessage + '\n');
                writer.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } else {
            try {
                new File(loggerFile.getPath()).createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(loggerFile, append));
                writer.write(logMessage + '\n');
                writer.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
