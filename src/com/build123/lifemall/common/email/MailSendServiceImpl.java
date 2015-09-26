package com.build123.lifemall.common.email;

import com.build123.lifemall.common.util.ReadFile;
import net.sf.json.JSONObject;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;
import java.util.Properties;

@Service
@Transactional
public class MailSendServiceImpl implements MailSendService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private SimpleMailMessage simpleMailMessage;

	public void sendByTemplate(Map param, String[] to, String subject, String templateName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
		String result = null;
		try {
	        messageHelper.setTo(to);
            messageHelper.setFrom(simpleMailMessage.getFrom());  
	        messageHelper.setSubject(subject);  
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", param);
            messageHelper.setText(result,true);   
		} catch (Exception e) {
			e.printStackTrace();
		}
		simpleMailMessage.setText(result);
		mailSender.send(mimeMessage);
	}
	
	public void sendText(String[] to, String subject, String content) {
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);
		mailSender.send(simpleMailMessage);
	}

    /**
     * admin注册成功后，发送密码至注册邮箱
     * @param to 邮件接受人
     * @param password 密码
     * @return
     */
    public static boolean sendMailByRegamin(String to,String password,String loginname,HttpServletRequest request){
        String subj="笑手物流管理系统管理员帐号添加";
        String text="尊敬的"+loginname+"用户,欢迎使用笑手物流管理系统！注册密码为："+password;
        return sendMail(subj,text,to,request);
    }

    /**
     * 发送邮件
     * @param subj 发送邮件标题
     * @param text 发送邮件内容
     * @param to   接收人
     * @return
     */
    @SuppressWarnings("deprecation")
    private static boolean sendMail(String subj,String text,String to,HttpServletRequest request){
        String fileName="sysconfig"+ File.separator+"emailset.json";
        String path=request.getRealPath(fileName);
        System.out.println("path:"+path);
        JSONObject emailJSON= JSONObject.fromObject(ReadFile.readFile(path));

        String host   = emailJSON.get("mail.smtp.host").toString();
        int    port   = Integer.parseInt(emailJSON.get("mail.smtp.port").toString());
        String user   = emailJSON.get("mail.smtp.user").toString();
        String passwd = emailJSON.get("mail.smtp.passwd").toString();
        //发信人
        String from = emailJSON.get("from").toString();
        //转发接收人
        String cc ="yindong0720@vip.qq.com";
        return sendMessage(to, cc, from, subj, text, user, passwd, host, port);
    }

    /**
     * 发送邮件消息
     * @param to 邮件接收人
     * @param cc 转发接收人
     * @param from 发件人
     * @param subj 邮件标题
     * @param text 邮件内容
     * @param login 发件帐号
     * @param password 发件密码
     * @param smtphost 发件服务器
     * @param port 服务端口
     * @return
     */
    private static boolean sendMessage(String to, String cc, String from, String subj, String text,
                                       final String login, final String password, String smtphost, int port){
        boolean ok = false;
        String logTo = "";
        try {
            // general
            Properties props = new Properties();
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.transport.protocol", "smtp");
            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);
            // content settings
            Message msg = new MimeMessage(session);
            msg.setContent("Content-Transfer-Encoding", "utf-8");
            msg.setContent("Content-Type", "text/html;charset=utf-8");
            // from
            msg.setFrom(new InternetAddress(from));
            // to
            InternetAddress[] toAddresses = new InternetAddress[1];
            //for (int i = 0; i < toArray.size(); i++) {
            toAddresses[0] = new InternetAddress(to);
            logTo = logTo + "to=" + to + ";";
            //}
            msg.addRecipients(Message.RecipientType.TO, toAddresses);
            // cc
            InternetAddress[] ccAddresses = new InternetAddress[1];
            //for (int i = 0; i < ccArray.size(); i++) {
            ccAddresses[0] = new InternetAddress(cc);
            logTo = logTo + "cc=" + cc + ";";
            //}
            //msg.addRecipients(Message.RecipientType.CC, ccAddresses);
            // body
            msg.setText(text);
            // subject
            msg.setSubject(subj);
            // do the hard work
            Transport transport = session.getTransport();
            transport.connect(smtphost, port, login, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            ok = true;
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

}
