package com.open01.logs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.open01.logs.db.DosageDatabase;
import com.open01.logs.model.Dosage;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class DosageBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */
	public DosageBean() {
		super("cmd");
		addPart(null, new Part0());
		addPart("WEL:DELETE", new Part1());
		addPart("WEL:DATASIZE", new Part2());
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}

	private final class Part0 extends DefaultPlainBean {
		private List<Dosage> dosages = null;
		@Override
		protected boolean doConditionIsTrue() {
			DosageDatabase database = new DosageDatabase(); 
			//根据USER_ID控制文件数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			dosages= database.getdosage(user_id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(dosages,response));
		}
	}
	private final class Part1 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			String id = request.getParameter("fid");
			DosageDatabase database = new DosageDatabase();
			database.updateid_delete(id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {

			writer.println("PART1 Test test.");
		}
	}
	/**
	 * 根据用户名，拿到该用户的最大可以使用流量和已经使用的流量
	 * @param user_id
	 * @author jeremy
	 * 
	 */
	private final class Part2 extends DefaultPlainBean {
		Map<String,String> map = new HashMap<String,String>();
		DosageDatabase database = new DosageDatabase();
		@Override
		protected boolean doConditionIsTrue() {
			//根据USER_ID控制文件数据权限 
			HttpSession sessions = request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			//根据用户ID拿到最大允许使用流量和已经使用流量
			int total_limit_size = database.getTotalLimitSize(user_id);
			int used_data_size = database.getUsedDataSize(user_id);
			map.put("totalSize", String.valueOf(total_limit_size));
			map.put("usedSize", String.valueOf(used_data_size));
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(map,response));
		}
	}
}
