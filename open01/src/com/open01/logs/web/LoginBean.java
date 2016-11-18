package com.open01.logs.web;

import javax.servlet.http.HttpSession;

import com.open01.logs.auth.User;
import com.open01.logs.db.LoginDatabase;
import com.open01.logs.db.ProjectsDatabase;
import com.open01.logs.util.CipherUtil;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class LoginBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */
	public LoginBean() {
		super("cmd");

		addPart(null, new Part0());
		addPart("WEL:DESTROY", new Part1());
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}

	private final class Part0 extends DefaultPlainBean {

		private User user = null;

		@Override
		protected boolean doConditionIsTrue() {

			String name = request.getParameter("username");
			LoginDatabase ld = new LoginDatabase();
			user = ld.selectUser(name);

			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			HttpSession session = request.getSession();
			if (user == null) {
				writer.write(JsonUtils.objectToJson(user,response));
			} else {
				String password = request.getParameter("password");
				//md5加密判断密码是否正确
				if (!CipherUtil.validatePassword((String)user.getPassword(), password) || password == null) {
					user.setStatu("fail");
				}
				
				if(CipherUtil.validatePassword((String)user.getPassword(), password)){
					session.setAttribute("user",user);
					session.setAttribute("user_name",user.getName());
					session.setAttribute("user_id",user.getUser_id());
					session.setAttribute("client_id",user.getClient_id());
					session.setAttribute("user_token",user.getUser_token());
					writer.write(JsonUtils.objectToJson(user,response));

				}
			}

		}
	}
	/*
	 * 将用户信息写入session
	 */
	private final class Part1 extends DefaultPlainBean {
	
		@Override
		protected boolean doConditionIsTrue() {
			HttpSession session = request.getSession(false);
			if(session != null){
				session.removeAttribute("user");  
				session.removeAttribute("user_id");  
				session.removeAttribute("user_name");  
				session.removeAttribute("client_id");  
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			
		}
	}

}
