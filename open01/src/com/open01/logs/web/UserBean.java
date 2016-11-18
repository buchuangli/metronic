package com.open01.logs.web;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.auth.User;
import com.open01.logs.db.UserDatabase;
import com.open01.logs.util.CipherUtil;
import com.open01.logs.util.Consant;
import com.open01.logs.util.GetTokenUtil;
import com.open01.logs.util.JsonUtils;
import com.open01.logs.util.MailUtil;
import com.open01.logs.util.Sendmail;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class UserBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */
	public UserBean() {
		super("cmd");
		addPart(null, new Part0());	
		addPart("getUserName", new Part1()); 
		addPart("WEL:FORGETPASSWORD", new Part2());
	}

	@Override
	public boolean isTrue() {
		return true;
	}

	UserDatabase ud = new UserDatabase();
	private final class Part0 extends DefaultPlainBean {

		private User user = new User();

		@Override
		protected boolean doConditionIsTrue() {
			String name = request.getParameter("username");
			String partname = request.getParameter("partname");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String passwd= request.getParameter("password");
			String company  = request.getParameter("company");
			String datasize  = request.getParameter("datasize");
			String user_token=GetTokenUtil.getToken(email, name);
			
			//md5加密
			String password = CipherUtil.generatePassword(passwd); 
	
			int limit_data_size=0;
			user.setClient_id(0);
			user.setUser_token(user_token);
			
			if(StringUtils.isNotEmpty(name)){
				user.setName(name);
			}
			if(StringUtils.isNotEmpty(partname)){
				user.setSectionName(partname);
			}
			if(StringUtils.isNotEmpty(email)){
				user.setEmail(email);
			}
			if(StringUtils.isNotEmpty(tel)){
				user.setPhone(tel);
			}
			if(StringUtils.isNotEmpty(password)){
				user.setPassword(password);
			}
			if(StringUtils.isNotEmpty(company)){
				user.setCompany(company);
			}
			if(StringUtils.isNotEmpty(datasize)){
				limit_data_size=Integer.valueOf(datasize);
				user.setLimit_data_size(limit_data_size*1024);
			}
			ud.insertUser(user);
			int id = ud.selectUserIdByName(name);
			ud.updateClientIdByUserId(id);
			
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(user, response));
		}
	}

	private final class Part1 extends DefaultPlainBean {

		private User user;

		@Override
		protected boolean doConditionIsTrue() {
			String name = request.getParameter("username");
			user = ud.selectUserName(name);
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(user, response));
		}
	}
	
	private final class Part2 extends DefaultPlainBean {

		private int res;

		@Override
		protected boolean doConditionIsTrue() {
		
			//判断用户名是否存在
			String name = request.getParameter("username");
			String mail = request.getParameter("email");
			User user = ud.selectUserName(name, mail);
			if(null == user.getName()){ 
				res = 0;
			}else{
				ud.updatePassworByUserNameAndMail(name, mail);
				try {
					Sendmail.sendSimpleMail(Consant.FORGET_PASSWORD_MAIL_TITLE,Consant.FORGET_PASSWORD_MAIL_CONTENT,mail);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//MailUtil.sendMail(mail, Consant.FORGET_PASSWORD_MAIL_TITLE, Consant.FORGET_PASSWORD_MAIL_CONTENT);
				res = 1;
			}
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(res, response));
		}
	}
}
