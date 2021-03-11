package com.Utility_Library;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class EmailReader {

    private HashMap<String, String> emailInfo = new HashMap<String,String>();
    private MailLogger mailLogger = MailLogger.getInstance();
    private ErrorLogger errorLogger = ErrorLogger.getInstance();
    private Store emailConnection;
    public boolean connectionStatus = false;
    public String saveToPath;
    public String attachmentName = "";

    public EmailReader(HashMap<String, String> emailInfo, String saveToPath) {
        this.emailInfo = emailInfo;
        this.saveToPath = saveToPath;
    }

    public void connectToMailBox() {
        try {
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            Properties properties = System.getProperties();
            properties.setProperty("mail.store.protocol", emailInfo.get("Protocol"));
            properties.setProperty("mail.imaps.auth.plain.disable", emailInfo.get("Authenticate Plain"));
            properties.setProperty("mail.imaps.partialfetch", emailInfo.get("PartialFetch"));
            properties.setProperty("mail.imaps.fetchsize", emailInfo.get("Fetchsize"));
            properties.put("mail.imaps.auth.ntlm.disable", emailInfo.get("Authenticate Ntlm"));

            Session emailSession = Session.getInstance(properties, null);
            emailConnection = emailSession.getStore("imaps");
            emailConnection.connect(
                    emailInfo.get("Host"),
                    emailInfo.get("User_Email") ,
                    emailInfo.get("Password"));
            if (emailConnection.isConnected())
            {
                mailLogger.addTextToLogFile("****************************************", false);
                mailLogger.logDate();
                connectionStatus = true;
            }
        } catch (MessagingException ex) {
            errorLogger.logException(ex);

        } catch (Exception ex) {
            errorLogger.logException(ex);
        }
    }

    public void closeMailBoxConnection() {
        try
        {
            if (emailConnection.isConnected())
            {
                emailConnection.close();
                mailLogger.addTextToLogFile("****************************************", true);
            }
        }
        catch (MessagingException ex)
        {
            errorLogger.logException(ex);
        }
    }

    public void getAttachments(String folderName, String emailSubject, String newName) {
        try
        {
            Message arrayMessages[] = null;
            Folder emailFolder = emailConnection.getFolder(folderName);
            emailFolder.open(Folder.READ_WRITE);
            SearchTerm flags = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            arrayMessages = emailFolder.search(flags);
            boolean foundMessage = false;

            System.out.println("Found: " + arrayMessages.length + " messages in Folder: " + folderName);
            mailLogger.addTextToLogFile("Found: " + arrayMessages.length + " messages in Folder: " + folderName, true);
            for (Message msg : arrayMessages)
            {
                try
                {
                    if (msg.getSubject().toUpperCase().contains(emailSubject.toUpperCase()))
                    {
                        String contentType = msg.getContentType();
                        if (contentType.contains("multipart"))
                        {
                            Multipart multipart = (Multipart) msg.getContent();
                            for (int counter = 0; counter < multipart.getCount(); counter++)
                            {
                                MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(counter);
                                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()))
                                {

                                    if(newName.isEmpty() || newName.isBlank())
                                        part.saveFile(saveToPath + part.getFileName());
                                    else part.saveFile(saveToPath + newName);

                                    attachmentName = part.getFileName();
                                    foundMessage = true;
                                    break;
                                }
                            }
                            if (foundMessage) break;
                        }
                    }
                }
                catch (IOException ex)
                {
                    errorLogger.logException(ex);
                }
            }
            emailFolder.setFlags(arrayMessages, new Flags(Flags.Flag.SEEN), true);
            emailFolder.close(false);
        }
        catch (MessagingException ex)
        {
            errorLogger.logException(ex);
        }
    }
}
