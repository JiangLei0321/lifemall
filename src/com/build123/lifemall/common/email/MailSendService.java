package com.build123.lifemall.common.email;

import java.util.Map;

public interface MailSendService {

	/**
	 * 使用模板发送邮件
	 * 
	 * @param param 模板中需要用的参数
	 * @param to 发送到
	 * @param subject 邮件标题
	 * @param templateName 模板名称
	 */
	void sendByTemplate(Map<String, String> param, String[] to, String subject, String templateName);

	/**
	 * 发送普通文本邮件
	 * 
	 * @param to 发送到
	 * @param subject 邮件标题
	 * @param content 邮件内容
	 */
	void sendText(String[] to, String subject, String content);
}
