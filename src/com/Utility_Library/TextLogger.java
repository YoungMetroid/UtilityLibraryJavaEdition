package com.Utility_Library;

public class TextLogger extends ErrorLogger{

    private static TextLogger single_Instance = null;
    protected TextLogger()
    {
        super();
    }
    public static TextLogger getInstance()
    {
        if (single_Instance == null)
            single_Instance = new TextLogger();
        return single_Instance;
    }
    protected TextLogger(String projectPath)
    {
        super(projectPath);
    }
    public static TextLogger getInstance(String projectPath)
    {
        if (single_Instance == null)
            single_Instance = new TextLogger(projectPath);
        return single_Instance;
    }

}
