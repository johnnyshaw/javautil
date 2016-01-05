package com.johnny.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Component;

import com.johnny.config.SendMailConfig;


/**
 * 
 * 邮件发送工具类
 *
 * 2015年8月25日
 */
@Component
public class SendEmailTool {

	private SendMailConfig sendEmailConfig;
	
	private static Session session;
	private static Transport transport;

	private static SendEmailTool sendEmailTool;

	public static synchronized SendEmailTool getInstance() {
		if (null != sendEmailTool) {
			return sendEmailTool;
		} else {
			sendEmailTool = new SendEmailTool();
			return sendEmailTool;
		}
	}

	/**
	 * 
	 * @Title: init
	 * @Description: 初始化邮件发送服务
	 * @Param @throws MessagingException
	 * @Return void
	 * @Throws
	 */
	public void init() throws MessagingException {
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sendEmailConfig.getMAIL_USERNAME(),
						sendEmailConfig.getMAIL_PASSWORD());
			}
		};
		Properties props = System.getProperties();
		props.put(sendEmailConfig.getMAIL_SMTP_HOST(), sendEmailConfig.getMAIL_HOST());
		props.put(sendEmailConfig.getMAIL_SMTP_AUTH(), "true");
		session = Session.getDefaultInstance(props, auth);
		transport = session.getTransport("smtp");
		transport.connect(sendEmailConfig.getMAIL_HOST(), sendEmailConfig.getMAIL_USERNAME(),
				sendEmailConfig.getMAIL_PASSWORD());

	}

	/**
	 * 
	 * @Title: sendAttachment
	 * @Description: 包含附件发送
	 * @Param @param subject
	 * @Param @param contents
	 * @Param @param receiverAccountNum
	 * @Param @param fileName 上传文件全路径及文件名称for example:E:\\licx.sql
	 * @Param @return
	 * @Return boolean
	 * @Throws
	 */
	public boolean sendAttachment(String subject, String contents,
			String receiverAccountNum, String fileName) {
		try {
			init();
			session.setDebug(false);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sendEmailConfig.getMAIL_FROM()));
			message.setSentDate(new Date());
			// 加载收件人地址
			if (receiverAccountNum.split(";").length > 1) {
				// 群发
				Address[] addresses = new Address[receiverAccountNum.split(";").length];
				for (int i = 0; i < addresses.length; i++) {
					addresses[i] = new InternetAddress(
							receiverAccountNum.split(";")[i]);
				}
				message.addRecipients(Message.RecipientType.TO, addresses);
				message.setSubject(subject);
				message.setText(contents);
				// 保存邮件
				message.saveChanges();
			} else {
				// 单发
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(receiverAccountNum));
				message.setSubject(subject);
				message.setText(contents);
				message.saveChanges();
			}
			if (fileName != null && fileName.length() > 0
					&& !"null".equals(fileName)) {
				MimeMultipart mimeMultipart = new MimeMultipart();
				MimeBodyPart part = new MimeBodyPart();
				part.setContent(contents, "text/html" + ";charset=GBK");
				mimeMultipart.addBodyPart(part);
				// 添加附件
				part = new MimeBodyPart();
				DataSource fds = new FileDataSource(fileName);
				part.setDataHandler(new DataHandler(fds));
				part.setFileName(MimeUtility.encodeText(fds.getName()));
				mimeMultipart.addBodyPart(part);
				message.setContent(mimeMultipart);
				message.saveChanges();

			}
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				close();
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 
	 * @Title: sendSimp
	 * @Description: TODO
	 * @Param @param mailReceiver 接收信息邮箱
	 * @Param @param subject 邮件标题
	 * @Param @param content 邮件内容
	 * @Param @return
	 * @Return boolean
	 * @Throws
	 */
	@SuppressWarnings("static-access")
	public boolean sendSimp(String mailReceiver, String subject, String content) {
		try {
			init();
			session.setDebug(false);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sendEmailConfig.getMAIL_FROM()));
			message.setSentDate(new Date());
			// 加载收件人地址
			if (mailReceiver.split(";").length > 1) {
				// 群发
				Address[] addresses = new Address[mailReceiver.split(";").length];
				for (int i = 0; i < addresses.length; i++) {
					addresses[i] = new InternetAddress(
							mailReceiver.split(";")[i]);
				}
				message.addRecipients(Message.RecipientType.TO, addresses);
				message.setSubject(subject);
				// message.setText(content);
				message.setContent(content, "text/html;charset=gbk");
				// 保存邮件
				message.saveChanges();
			} else {
				// 单发
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(mailReceiver));
				message.setSubject(subject);
				// message.setText(content);
				message.setContent(content, "text/html;charset=gbk");
				message.saveChanges();
			}
			transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				close();
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 
	 * @Title: close
	 * @Description: TODO
	 * @Param @throws MessagingException
	 * @Return void
	 * @Throws
	 */
	private static void close() throws MessagingException {
		if (transport != null) {
			transport.close();
		}
	}
}
