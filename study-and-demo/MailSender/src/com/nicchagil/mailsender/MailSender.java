package com.nicchagil.mailsender;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSender extends AbstractSessionMailSender {
    
    private MailSenderConfig c;
    private MimeMessage message;
    
    public MailSender(MailSenderConfig config) throws Exception {
        super();
        this.c = config;
        this.setConfig();
    }
    
    public MailSender(MailSenderConfig config, Session session) throws Exception {
        super();
        this.c = config;
        this.setConfig();
        super.setSession(session);
    }
    
    /**
     * �����ʼ�
     * @throws MessagingException
     */
    public void send() throws MessagingException {
        Transport.send(message);
    }
    
    /**
     * ��ȡMimeMessage����¶�˶����Ա��ڿ������������ø��Ի������ԣ��˹����಻֧�ֵķ������ɿ�����Ա�������ã�����������û�����
     * @return
     */
    public MimeMessage getMessage() {
        return message;
    }

    /**
     * ����MimeMessage����¶�˶����Ա��ڿ������������ø��Ի������ԣ��˹����಻֧�ֵķ������ɿ�����Ա�������ã�����������û�����
     * @return
     */
    public void setMessage(MimeMessage message) {
        this.message = message;
    }
    
    /**
     * ����Java Mail����
     * @throws Exception
     */
    private void setConfig() throws Exception {
        this.configValid();
        
        if (session == null) {
            session = initSession(c);
        }
        message = new MimeMessage(session);
        
        /* ������ */
        Address[] fromMailArray = new Address[1];
        fromMailArray[0] = new InternetAddress(c.getFromMail());
        message.addFrom(fromMailArray);
        
        if (c.getToMails() != null && c.getToMails().size() > 0) {
            for (String mail : c.getToMails()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            }
        }
        if (c.getCcMails() != null && c.getCcMails().size() > 0) {
            for (String mail : c.getCcMails()) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(mail));
            }
        }
        if (c.getToMails() != null && c.getToMails().size() > 0) {
            for (String mail : c.getToMails()) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail));
            }
        }
        
        // �ʼ�����
        message.setSubject(c.getSubject());
        
        /* ���� */
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(c.getContent(), c.getContentType());
        
        /* ��װ�ʼ���������Ϣ */
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);
        
        /* ���� */
        BodyPart attachmentPart = null;
        DataSource ds = null;
        if (c.getAttachments() != null && c.getAttachments().size() > 0) {
            for (Attachment a : c.getAttachments()) {
                attachmentPart = new MimeBodyPart();
                ds = new FileDataSource(a.getFile());
                attachmentPart.setDataHandler(new DataHandler(ds));
                attachmentPart.setFileName(MimeUtility.encodeText(a.getFilename()));
                
                multipart.addBodyPart(attachmentPart);
            }
        }
        
        message.setContent(multipart);
    }
    
    /**
     * ����У��
     * @throws Exception
     */
    private void configValid() throws Exception {
        if (c == null) {
            throw new Exception("���ö���Ϊ��");
        }
        
        if (c.getSMTPMailHost() == null || c.getSMTPMailHost().length() == 0) {
            throw new Exception("SMTP������Ϊ��");
        }
        
        if (c.getFromMail() == null && c.getFromMail().length() == 0) {
            throw new Exception("�������ʼ�Ϊ��");
        }
        
        if (c.getToMails() == null || c.getToMails().size() < 1) {
            throw new Exception("�ռ����ʼ�Ϊ��");
        }
        
        if (c.getSubject() == null || c.getSubject().length() == 0) {
            throw new Exception("�ʼ�����Ϊ��");
        }
        
        if (c.getContent() == null || c.getContent().length() == 0) {
            throw new Exception("�ʼ�����Ϊ��");
        }
    }
    
}
