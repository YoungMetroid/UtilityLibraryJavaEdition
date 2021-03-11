package com.Utility_Library;

import java.io.File;
import java.util.HashMap;

public class TestMain {

    public static void main(String[] args) {
        //Test functions
        ErrorLogger errorLogger = ErrorLogger.getInstance();

        HashMap<String, String> emailInfo = new HashMap<>();
        emailInfo.put("Host", "outlook.office365.com");
        emailInfo.put("User", "felipe.elizalde1@hp.com");
        emailInfo.put("Email", "delivery.eta.tool@hp.com");
        emailInfo.put("Password", "FoxShine9.1");
        emailInfo.put("Protocol", "imaps");
        emailInfo.put("Authenticate Plain", "true");
        emailInfo.put("Authenticate Ntlm", "true");
        emailInfo.put("PartialFetch", "false");
        emailInfo.put("Fetchsize", "500000");

        String filePath =
                "C:" +
                        File.separator +
                        "HP Projects" +
                        File.separator;

        //Third Step - Create emailReader instance and test connection with the information
        //In emailInfo
        EmailReader emailReader = new EmailReader(emailInfo, filePath);
        emailReader.connectToMailBox();

        emailReader.getAttachments("OSS",
                "HP OS Report",
                "ossReport.txt");
    }
}
