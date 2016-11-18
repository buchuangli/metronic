package com.open01.logs.util;

public class HadoopShellComandThread extends Thread{
	private String str; 
	public HadoopShellComandThread(String str){ 
		this.str = str; 
	} 
    @Override
    //执行es的shell命令
    public void run(){
    	RemoteShellTool tool = new RemoteShellTool(Consant.HADOOP_SHELL_SCRIPT_HOST_ADDRESS,Consant.HADOOP_SHELL_SCRIPT_USER_NAME, Consant.HADOOP_SHELL_SCRIPT_USER_PASSWD, "utf-8");
			System.out.println("连接成功hadoop");
			String exec = tool.exec(str);
			System.out.println(exec); 
			MailUtil.sendMail("xianghong.dai@open01.com", "连接成功hadoop", str+"--"+exec);
			if(null != exec){
				this.interrupt(); 
			}
			
    }
    public static void main(String[] args)
    {
    	EsShellComandThread thread = new EsShellComandThread(" 122 12 323232");
        thread.start();
    }
}