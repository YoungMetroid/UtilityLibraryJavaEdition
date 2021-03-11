package com.Utility_Library;

public class MailLogger extends ErrorLogger {

    private static MailLogger single_Instance = null;

    public MailLogger() {
        super();
    }

    public static MailLogger getInstance() {
        if (single_Instance == null)
            single_Instance = new MailLogger();
        return single_Instance;
    }

    public MailLogger(String projectPath) {
        super(projectPath);
    }

    public static MailLogger getInstance(String projectPath) {
        if (single_Instance == null)
            single_Instance = new MailLogger(projectPath);
        return single_Instance;
    }
}
