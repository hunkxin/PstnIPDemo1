package com.yike.json;

import java.io.IOException;
import java.util.List;

import com.yike.DAO.DbOperation;
import com.yike.clicmd.LinuxCMD;
import com.yike.DAO.Confid;
import com.yike.DAO.DbContent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonOperation {
	public String tojson(String datatype,String confid){
		ObjectMapper mapper = new ObjectMapper();
       //JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(System.out,JsonEncoding.UTF8);
		DbOperation op = new DbOperation();
		try {
			if(datatype.equals("members")){
				List<DbContent> contents = op.getdbcontent(confid);
				for(DbContent content:contents){
					if(content.getUuid()==null){
						content.setUuid("0");
					}
				}
				//System.out.println(mapper.writeValueAsString(contents));
				return mapper.writeValueAsString(contents);
			}else if(datatype.equals("conference_id")){
				List<Confid> confids = op.getdbconfid();
				//System.out.println(mapper.writeValueAsString(confids));
				return mapper.writeValueAsString(confids);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
//       for(Dbcontent content:contents){
//          try {
//        	  //generator.writeObject(person);
//        	  //mapper.writeValue(System.out, content);
//        	  System.out.println(mapper.writeValueAsString(content)); 
//          } catch (JsonGenerationException e) {
//        	  // TODO Auto-generated catch block
//        	  e.printStackTrace();
//        	  System.out.println(e);
//          } catch (JsonMappingException e) {
//        	  // TODO Auto-generated catch block
//        	  e.printStackTrace();
//        	  System.out.println(e);
//          } catch (IOException e) {
//        	  // TODO Auto-generated catch block
//        	  e.printStackTrace();
//        	  System.out.println(e);
//           }
//          System.out.println("----------------"); 
//       }
	}
	
	public String tojsontext(List<DbContent> contents,String isupdate){
		ObjectMapper mapper = new ObjectMapper();
		DbOperation op = new DbOperation();
		String confid = isupdate;
		if(isupdate.equals("newid")){
			if(op.getmaxconid()==null||op.getmaxconid()==""){
				return "{\"confid\":\"0\"}";
			}
			else
				confid = String.valueOf((Integer.parseInt(op.getmaxconid())+1));
		}		
		for(DbContent content:contents){
			content.setConference_id(confid);
			content.setUuid("0");
		}
		op.deletedb(confid);
		try {
			if(op.insertdb(contents))
				return mapper.writeValueAsString(new Confid(confid));
			else
				return mapper.writeValueAsString(new Confid("0"));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"confid\":\"0\"}";
		}
	}
	
	public String tojsontext(String confid){
		DbOperation op = new DbOperation();
		List<String> users = op.getsingleconfuser(confid);
		LinuxCMD cmd = new LinuxCMD();
		String cmdpara;
		final String domain_name = "101.200.215.49";
		for(String user:users){
			//System.out.println(user.substring(1));
			if(user.length()<7||!user.matches("[0-9]+")){
				cmdpara = "bgapi originate {ignore_early_media=ring_ready}user/"+user+"@"+domain_name+" &conference(PSTNIP-"+confid+"-"+domain_name+"@cdquality)";
			}else{
				if(user.substring(0, 1).equals("0")){
					user = user.substring(1);
				}
				cmdpara = "bgapi originate {ignore_early_media=ring_ready}sofia/gateway/vos/0"+user+" &conference(PSTNIP-"+confid+"-"+domain_name+"@cdquality)";
			}
			try {
				cmd.runCommand(cmdpara);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "{\"dialallok\":\"complete\"}";
	}
}
