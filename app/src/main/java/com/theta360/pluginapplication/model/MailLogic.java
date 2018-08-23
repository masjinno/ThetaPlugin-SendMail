package com.theta360.pluginapplication.model;

import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailLogic {
    private static final String LOG_TAG = "SendMail";
    private static final String ACCOUNT = "masjinno";
    private static final String PASS = "HOGEHOGE";

    public void send() {
        Log.d(LOG_TAG, "send in");
        // UIスレッド以外で実行
        try {

            final Properties property = new Properties();
            property.put("mail.smtp.host", "smtp.gmail.com");
            property.put("mail.host", "smtp.gmail.com");
            property.put("mail.smtp.port", "465");
            property.put("mail.smtp.socketFactory.port", "465");
            property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            // セッション
            Log.d(LOG_TAG, "before session");
            final Session session = Session.getInstance(property, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    Log.d(LOG_TAG, "getPasswordAuthentication");
                    return new PasswordAuthentication(ACCOUNT, PASS);
                }
            });
            Log.d(LOG_TAG, "after session");

            MimeMessage mimeMsg = new MimeMessage(session);
            String destAddress = "hogehoge@hogehoge";

            mimeMsg.setSubject("SampleSubject", "utf-8");
            mimeMsg.setFrom(new InternetAddress(ACCOUNT + "@gmail.com"));
            mimeMsg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(destAddress));

            // 添付ファイル
            final MimeBodyPart txtPart = new MimeBodyPart();
            txtPart.setText("SampleMessage", "utf-8");

//            final MimeBodyPart filePart = new MimeBodyPart();
//            File file = new File("[添付ファイルパス]");
//            FileDataSource fds = new FileDataSource(file);
//            DataHandler data = new DataHandler(fds);
//            filePart.setDataHandler(data);
//            filePart.setFileName(MimeUtility.encodeWord("[メール本文の添付ファイル名]"));

            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(txtPart);
//            mp.addBodyPart(filePart);
            mimeMsg.setContent(mp);

            // メール送信する。
            Log.d(LOG_TAG, "before send");
            final Transport transport = session.getTransport("smtp");
            transport.connect(ACCOUNT, PASS);
            transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
            transport.close();
            Log.d(LOG_TAG, "after send");

        } catch (MessagingException e) {
            Log.e(LOG_TAG, "MessagingException", e);

//        } catch (UnsupportedEncodingException e) {

        } finally {

        }
    }
}
