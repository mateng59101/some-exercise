package com.nicchagil.mailsender;

import java.io.File;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class ReadMe {
    
	/* �������Ϣ */
    String SMTP_MAIL_HOST = "smtp.163.com"; // ���ʼ���������ַ������ȥ�����ʼ�����������ҳ��ѯ
    String EMAIL_USERNAME = "example@163.com";
    String EMAIL_PASSWORD = "password";
    String TO_EMAIL_ADDRESS_1 = "example@hotmail.com";
    /* ѡ�����Ϣ */
    String TO_EMAIL_ADDRESS_2 = "example@qq.com";

    // @Test
    public void case1() throws Exception {
        /* ʹ�����һ������ʹ�� */
        MailSenderConfig c = new MailSenderConfig(SMTP_MAIL_HOST, 
                "ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ�һ�����⣩", "ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ�һ", EMAIL_USERNAME);
        c.setUsername(EMAIL_USERNAME);
        c.setPassword(EMAIL_PASSWORD);
        c.addToMail(TO_EMAIL_ADDRESS_1);
        c.addToMail(TO_EMAIL_ADDRESS_2);
        c.addCcMail(TO_EMAIL_ADDRESS_2);
        c.addCcMail(TO_EMAIL_ADDRESS_1);
        c.addAttachment(new Attachment(new File("d:/1.txt")));
        
        MailSender ms = new MailSender(c);
        ms.send();
        System.out.println("sent...");
    }
    
    @Test
    public void case2() throws Exception {
        /* ʹ����������ڸ�������£����������������ò����������󣬹ʽ�MimeMessage��¶�Ա��ڿ������������ø��Ի������� */
        MailSenderConfig c = new MailSenderConfig(SMTP_MAIL_HOST, 
                "ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ��������⣩", "ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ���", EMAIL_USERNAME);
        c.setUsername(EMAIL_USERNAME);
        c.setPassword(EMAIL_PASSWORD);
        c.addToMail(TO_EMAIL_ADDRESS_1);
        c.addToMail(TO_EMAIL_ADDRESS_2);
        c.addCcMail(TO_EMAIL_ADDRESS_2);
        c.addCcMail(TO_EMAIL_ADDRESS_1);
        c.addAttachment(new Attachment(new File("d:/1.txt")));
        
        MailSender ms = new MailSender(c);
        
        MimeMessage message = ms.getMessage();
        message.setContent("ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ������滻��", "text/html;charset=utf-8");
        ms.setMessage(message);
        
        ms.send();
        System.out.println("sent...");
    }
    
    // @Test
    public void case3() throws Exception {
        /* ʹ�����������η����ʼ����ɻ���Session��ʹ��η����ʼ��������Session���Լ����ظ�����Session
         * ͬʱ��ע�⻺���Session��ʱЧ��
         */
        MailSenderConfig c = new MailSenderConfig(SMTP_MAIL_HOST, 
                "ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ��������⣩", "ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ���", EMAIL_USERNAME);
        c.setUsername(EMAIL_USERNAME);
        c.setPassword(EMAIL_PASSWORD);
        c.addToMail(TO_EMAIL_ADDRESS_1);
        c.addToMail(TO_EMAIL_ADDRESS_2);
        c.addCcMail(TO_EMAIL_ADDRESS_2);
        c.addCcMail(TO_EMAIL_ADDRESS_1);
        c.addAttachment(new Attachment(new File("d:/1.txt")));
        
        Session session = MailSender.initSession(c);
        
        MailSender ms = new MailSender(c, session);
        ms.send();
        
        c.setSubject("ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ������滻���⣩");
        c.setContent("ѧϰJava Mail�ʼ����ͣ���Ϊ�����ʼ������滻��");
        ms = new MailSender(c, session);
        ms.send();
        System.out.println("sent...");
    }

}
