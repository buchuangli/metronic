package com.open01.logs.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.open01.logs.db.RecycleDatabase;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class RecycleBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */
	public RecycleBean() {
		super("cmd");

		addPart(null, new Part0());
		addPart("WEL:RECOVER", new Part1());
		addPart("WEL:Delete", new Part2());
		addPart("WEL:DELETESELECT", new Part3());
		addPart("WEL:RECOVERSELECT", new Part4());
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}

	private final class Part0 extends DefaultPlainBean {

		private List<Project> projects = null;

		@Override
		protected boolean doConditionIsTrue() {

			//根据USER_ID控制文件数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			
			RecycleDatabase rd = new RecycleDatabase();
			projects = rd.getProjects(user_id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(projects,response));
		}
	}
	private final class Part1 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			int id = Integer.parseInt(request.getParameter("id"));
			RecycleDatabase rd = new RecycleDatabase();
			rd.updateid_delete(id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.println("PART1 Test test.");
		}
	}
	private final class Part2 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			int id = Integer.parseInt(request.getParameter("id"));
			RecycleDatabase rd = new RecycleDatabase();
			rd.delete(id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.println("PART1 Test test.");
		}
	}
	private final class Part3 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			String[] id = request.getParameterValues("ids[]");
			RecycleDatabase rd = new RecycleDatabase();
			rd.deletes(id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.println("PART1 Test test.");
		}
	}
	private final class Part4 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			String[] id = request.getParameterValues("ids[]");
			RecycleDatabase rd = new RecycleDatabase();
			rd.recover(id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.println("PART1 Test test.");
		}
	}

}
