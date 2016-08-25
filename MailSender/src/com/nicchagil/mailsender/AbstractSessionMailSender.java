package com.nicchagil.mailsender;

import java.util.Properties;

import javax.mail.Session;

public abstract class AbstractSessionMailSender {
    
    protected Session session;
    
    /**
     * ��ʼ��Session
     * @return
     */
    public static Session initSession(MailSenderConfig c) {
        Properties props = new Properties();
        if (c.getSMTPMailHost() != null && c.getSMTPMailHost().length() > 0) {
            props.put("mail.smtp.host", c.getSMTPMailHost());
        }
        
        if (c.getUsername() != null && c.getUsername().length() > 0 && 
                c.getPassword() != null && c.getPassword().length() > 0) {
            props.put("mail.smtp.auth", "true");
            return Session.getDefaultInstance(props, new SimpleAuthenticator(c.getUsername(), c.getPassword()));
        } else {
            props.put("mail.smtp.auth", "false");
            return Session.getDefaultInstance(props);
        }
    }

    /**
     * ��¶Getter��Setter�ṩSession�Ŀ������ԣ���֧�����������ʼ�/���Ͷ���ʼ�ʱ���ɻ���Session
     * @return
     */
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
}
