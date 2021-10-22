//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailAttachmentReceiver {
    @Value("${FOLDER_ATTACHMENT_SAVE}")
    String SAVE_DIRECTORY;
    @Value("${EMAIL_HOST}")
    String EMAIL_HOST;
    @Value("${EMAIL_USERNAME}")
    String EMAIL_USERNAME;
    @Value("${EMAIL_PASSWORD}")
    String EMAIL_PASSWORD;
    @Value("${EMAIL_PORT}")
    Integer EMAIL_PORT;
    @Value("${EMAIL_PO_SENDER}")
    String EMAIL_PO_SENDER;
    private static Logger log = Logger.getLogger(EmailAttachmentReceiver.class);

    public EmailAttachmentReceiver() {
    }

    public boolean downloadAttachmentFromNewEmailImap() throws MessagingException {
        boolean isNewEmail = false;
        Session session = Session.getDefaultInstance(new Properties());
        Store store = session.getStore("imaps");
        store.connect(this.EMAIL_HOST, this.EMAIL_PORT, this.EMAIL_USERNAME, this.EMAIL_PASSWORD);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(2);
        Message[] messages = inbox.search(this.buildEmailSearchFilters());
        if (messages.length > 0) {
            isNewEmail = true;
            Arrays.sort(messages, (m1, m2) -> {
                try {
                    return m2.getSentDate().compareTo(m1.getSentDate());
                } catch (MessagingException var3) {
                    throw new RuntimeException(var3);
                }
            });
            Message[] var6 = messages;
            int var7 = messages.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Message message = var6[var8];
                if (log.isDebugEnabled()) {
                    log.debug("sendDate: " + message.getSentDate() + " subject:" + message.getSubject() + " Content:");
                }

                this.processMessageBody(message);
            }
        }

        System.out.println("New Email:" + isNewEmail);
        return isNewEmail;
    }

    public void processMessageBody(Message message) {
        try {
            Object content = message.getContent();
            if (content instanceof String) {
                log.debug(content);
            } else if (content instanceof Multipart) {
                Multipart multiPart = (Multipart)content;
                this.procesMultiPart(multiPart);
            } else if (content instanceof InputStream) {
                InputStream inStream = (InputStream)content;

                int ch;
                while((ch = inStream.read()) != -1) {
                    System.out.write(ch);
                }
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        } catch (MessagingException var6) {
            var6.printStackTrace();
        }

    }

    public void procesMultiPart(Multipart multipartContent) throws MessagingException, IOException {
        String attachFiles = "";
        String messageContent = "";
        int numberOfParts = multipartContent.getCount();

        for(int partCount = 0; partCount < numberOfParts; ++partCount) {
            MimeBodyPart part = (MimeBodyPart)multipartContent.getBodyPart(partCount);
            if ("attachment".equalsIgnoreCase(part.getDisposition())) {
                String fileName = part.getFileName();
                attachFiles = attachFiles + fileName + ", ";
                part.saveFile(this.SAVE_DIRECTORY + File.separator + fileName);
            } else {
                messageContent = part.getContent().toString();
            }
        }

        if (attachFiles.length() > 1) {
            attachFiles.substring(0, attachFiles.length() - 2);
        }

    }

    public void downloadEmailAttachments(String host, String port, String userName, String password) {
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", port);
        properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));
        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore("pop3");
            store.connect(userName, password);
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(2);
            Message[] arrayMessages = folderInbox.getMessages();

            for(int i = 0; i < arrayMessages.length; ++i) {
                Message message = arrayMessages[i];
                Address[] fromAddress = message.getFrom();
                String contentType = message.getContentType();
                String messageContent = "";
                String attachFiles = "";
                if (!contentType.contains("multipart")) {
                    if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                        Object content = message.getContent();
                        if (content != null) {
                            messageContent = content.toString();
                        }
                    }
                } else {
                    Multipart multiPart = (Multipart)message.getContent();
                    int numberOfParts = multiPart.getCount();

                    for(int partCount = 0; partCount < numberOfParts; ++partCount) {
                        MimeBodyPart part = (MimeBodyPart)multiPart.getBodyPart(partCount);
                        if ("attachment".equalsIgnoreCase(part.getDisposition())) {
                            String fileName = part.getFileName();
                            attachFiles = attachFiles + fileName + ", ";
                            part.saveFile(this.SAVE_DIRECTORY + File.separator + fileName);
                        } else {
                            messageContent = part.getContent().toString();
                        }
                    }

                    if (attachFiles.length() > 1) {
                        attachFiles.substring(0, attachFiles.length() - 2);
                    }
                }
            }

            folderInbox.setFlags(arrayMessages, new Flags(Flag.SEEN), true);
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException var21) {
            log.error("No provider for pop3.");
            var21.printStackTrace();
            log.error(var21);
        } catch (MessagingException var22) {
            log.error("Could not connect to the message store");
            var22.printStackTrace();
            log.error(var22);
        } catch (IOException var23) {
            var23.printStackTrace();
            log.error(var23);
        }

    }

    SearchTerm buildEmailSearchFilters() throws AddressException {
        SearchTerm sender = new FromTerm(new InternetAddress(this.EMAIL_PO_SENDER));
        Flags seen = new Flags(Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        new SubjectTerm("");
        SearchTerm searchTerms = new AndTerm(unseenFlagTerm, sender);
        return searchTerms;
    }
}
