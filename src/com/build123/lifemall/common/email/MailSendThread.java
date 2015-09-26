package com.build123.lifemall.common.email;

import java.util.Map;

/**
 * Created by changqing.cui on 14-4-3.
 */
public class MailSendThread implements Runnable {

    private MailSendService mailSendService;
    private Map<String, String> param;
    private String subject;
    String[] toEmail;
    String emailTemplateName;

    /**
     *
     * @param toEmail 收件人
     * @param subject 邮件标题
     * @param emailTemplateName 邮件模板名称
     * @param param 邮件模板中需要使用的参数
     * @param mailSendService 发送邮件服务类实体bean
     */
    public MailSendThread(String[] toEmail,String subject,String emailTemplateName,Map<String, String> param,MailSendService mailSendService) {
        this.subject = subject;
        this.toEmail = toEmail;
        this.emailTemplateName = emailTemplateName;
        this.param = param;
        this.mailSendService = mailSendService;
    }

    @Override
    public void run() {
        mailSendService.sendByTemplate(param,toEmail,subject,emailTemplateName);
    }
}
