package com.open01.logs.web;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.auth.User;
import com.open01.logs.db.LoginDatabase;
import com.open01.logs.db.OptionsDatabase;
import com.open01.logs.util.CipherUtil;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class OptionsBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */
	public OptionsBean() {
		super("cmd");

		addPart(null, new Part0());
		addPart("WEL:UPDATE", new Part1());
		addPart("WEL:RESET", new Part2());
		addPart("WEL:RESETPASSWORD", new Part3());
		addPart("WEL:VALIDATEPASSWORD", new Part4());
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
			
			HttpSession sessions=request.getSession();
			int user_id = (int)sessions.getAttribute("user_id");
			
			LoginDatabase ld = new LoginDatabase();
			user = ld.selectByUserId(user_id);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(user,response));
		}
	}
	
	private final class Part1 extends DefaultPlainBean {
		LoginDatabase ld = new LoginDatabase();
		User user =null;
		@Override
		protected boolean doConditionIsTrue() {
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			user = ld.selectByUserId(user_id);
			
			String name = request.getParameter("username");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String sectionName = request.getParameter("sectionName");
			String headImgDataUrl = request.getParameter("headImgDataUrl");
			
			user.setUser_id(user_id);
			if(StringUtils.isNotEmpty(name)){
				user.setName(name);
			}
			if(StringUtils.isNotEmpty(email)){
				user.setEmail(email);
			}
			if(StringUtils.isNotEmpty(phone)){
				user.setPhone(phone);
			}
			if(StringUtils.isNotEmpty(sectionName)){
				user.setSectionName(sectionName);
			}
			if(StringUtils.isNotEmpty(headImgDataUrl)){
				user.setImage(headImgDataUrl);
			}
			
			OptionsDatabase od  =new OptionsDatabase();
			od.updateUser(user);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			user.setStatu("success");
			writer.write(JsonUtils.objectToJson(user,response));
		}
	}
	private final class Part2 extends DefaultPlainBean {
		
		private User user = new User();
		
		@Override
		protected boolean doConditionIsTrue() {
			
			LoginDatabase ld = new LoginDatabase();
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			user = ld.selectByUserId(user_id);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			user.setStatu("success");
			writer.write(JsonUtils.objectToJson(user,response));
		}
	}
	private final class Part3 extends DefaultPlainBean {
		
		private User user = new User();
		
		@Override
		protected boolean doConditionIsTrue() {
			User u = new User();
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String passwd = request.getParameter("password");
			String password = CipherUtil.generatePassword(passwd); 
			
			u.setUser_id(user_id);
			u.setPassword(password);
			OptionsDatabase od  =new OptionsDatabase();
			user = od.updatePass(u);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(user,response));
		}
	}
	private final class Part4 extends DefaultPlainBean {
		
		private  String status ="";
		
		@Override
		protected boolean doConditionIsTrue() {
			String pass = request.getParameter("pass");
			String password = request.getParameter("password");
			if (CipherUtil.validatePassword(pass, password)){
				status="success";
			}else{
				status="fail";
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(status,response));
		}
	}

}
