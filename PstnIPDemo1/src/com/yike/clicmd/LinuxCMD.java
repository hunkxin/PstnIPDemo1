package com.yike.clicmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LinuxCMD {
	
	private String cmd;
	
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public LinuxCMD(String cmd) {
		this.cmd = cmd;
	}
	
	public LinuxCMD() {
		
	}

	public List<String> runCommand(String tcmd) throws IOException, InterruptedException{
		final String cmd = tcmd;
		String[] cmds = {"/bin/sh","-c","/usr/local/freeswitch/bin/fs_cli -x "+"'"+cmd+"'"};  
        Process pro = Runtime.getRuntime().exec(cmds);  
        pro.waitFor();  
        InputStream in = pro.getInputStream();  
        BufferedReader read = new BufferedReader(new InputStreamReader(in));  
        String line = null;  
        List<String> messages = new ArrayList<String>();
        while((line = read.readLine())!=null){  
//            System.out.println(line);
            messages.add(line);
        }
        in.close();
        read.close();
        pro.destroy();
//      System.out.println(messages.size());
        return messages; 
	}    
}
