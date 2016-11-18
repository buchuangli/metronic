package com.open01.logs.util;

public class EsShellComandThread extends Thread{
	private String str; 
	public EsShellComandThread(String str){ 
		this.str = str; 
	} 
    @Override
    //执行es的shell命令
    public void run(){
			RemoteShellTool tooles = new RemoteShellTool(Consant.SHELL_SCRIPT_HOST_ADDRESS, Consant.SHELL_SCRIPT_USER_NAME,Consant.SHELL_SCRIPT_USER_PASSWD, "utf-8");
			System.out.println("连接成功es");
			MailUtil.sendMail("xianghong.dai@open01.com", "连接成功es", str);
			String exec = tooles.exec(str);
			MailUtil.sendMail("xianghong.dai@open01.com", "连接成功es", str+"--"+exec);
			System.out.println(exec); 
			if(null != exec){
				this.interrupt(); 
			}
    }
}