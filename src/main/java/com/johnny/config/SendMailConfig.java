package com.johnny.config;

/**
 * 邮件发送配置，以后可迁移至zookeeper上
 * @author johnnyShaw
 *
 */
public class SendMailConfig {
	
//	@ZkConfigAnnotation(path="/email/mailHost",defaultValue="smtp.exmail.qq.com")
	private String MAIL_HOST = "smtp.exmail.qq.com";
	
//	@ZkConfigAnnotation(path="/email/mailSmtpHost",defaultValue="mail.smtp.host")
	private  String MAIL_SMTP_HOST = "mail.smtp.host";
	
//	@ZkConfigAnnotation(path="/email/mailSmtpAuth",defaultValue="mail.smtp.auth")
	private String MAIL_SMTP_AUTH = "mail.smtp.auth";
	
//	@ZkConfigAnnotation(path="/email/mailFrom",defaultValue="service1@xxx.com")
	private String MAIL_FROM = "service1@xxx.com";
	
//	@ZkConfigAnnotation(path="/email/mailUserName",defaultValue="service1@xxx.com")
	private  String MAIL_USERNAME = "service1@xxx.com";
	
//	@ZkConfigAnnotation(path="/email/mailPassword",defaultValue="123456")
	private String MAIL_PASSWORD = "123456";
	
//	@ZkConfigAnnotation(path="/email/mailKey",defaultValue="key")
	private  String MAIL_KEY = "key";
	
//	@ZkConfigAnnotation(path="/email/checkUrl",defaultValue="https://xxx.xxx.com/forreset.htm")
	private  String CHECK_URL="https://xxx.xxx.com/forreset.htm";

	public String getMAIL_HOST() {
		return MAIL_HOST;
	}

	public void setMAIL_HOST(String mAIL_HOST) {
		MAIL_HOST = mAIL_HOST;
	}

	public String getMAIL_SMTP_HOST() {
		return MAIL_SMTP_HOST;
	}

	public void setMAIL_SMTP_HOST(String mAIL_SMTP_HOST) {
		MAIL_SMTP_HOST = mAIL_SMTP_HOST;
	}

	public String getMAIL_SMTP_AUTH() {
		return MAIL_SMTP_AUTH;
	}

	public void setMAIL_SMTP_AUTH(String mAIL_SMTP_AUTH) {
		MAIL_SMTP_AUTH = mAIL_SMTP_AUTH;
	}

	public String getMAIL_FROM() {
		return MAIL_FROM;
	}

	public void setMAIL_FROM(String mAIL_FROM) {
		MAIL_FROM = mAIL_FROM;
	}

	public String getMAIL_USERNAME() {
		return MAIL_USERNAME;
	}

	public void setMAIL_USERNAME(String mAIL_USERNAME) {
		MAIL_USERNAME = mAIL_USERNAME;
	}

	public String getMAIL_PASSWORD() {
		return MAIL_PASSWORD;
	}

	public void setMAIL_PASSWORD(String mAIL_PASSWORD) {
		MAIL_PASSWORD = mAIL_PASSWORD;
	}

	public String getMAIL_KEY() {
		return MAIL_KEY;
	}

	public void setMAIL_KEY(String mAIL_KEY) {
		MAIL_KEY = mAIL_KEY;
	}

	public String getCHECK_URL() {
		return CHECK_URL;
	}

	public void setCHECK_URL(String cHECK_URL) {
		CHECK_URL = cHECK_URL;
	}
	
	
}
