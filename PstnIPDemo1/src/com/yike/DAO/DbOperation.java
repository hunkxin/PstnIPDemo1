package com.yike.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbOperation {
	
	public DbOperation() {
		
	}

	public List<DbContent> getdbcontent(String confid){
		List<DbContent> contents = new ArrayList<DbContent>();
		Connection con = new JDBCPostgresql().getconnect();
		try {
			Statement sql = con.createStatement();
			String sqlcmd = "select * from fsw_pstn_callin where conference_id='"+confid+"' order by caller_name,caller_password";
			//System.out.println(sqlcmd);
			ResultSet res = sql.executeQuery(sqlcmd);
			//int i=0;
			while(res.next())
			{
				DbContent content = new DbContent(confid, 
												  res.getString("caller_name"), 
												  res.getString("caller_password"), 
												  res.getString("isused"));
				//i++;
				contents.add(content);
			}
			//System.out.println(i);
			res.close();
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sorry,there are some problems when connect to database!");
			contents = null;
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return contents;
	}
	
	public List<String> getsingleconfuser(String confid){
		List<String> users = new ArrayList<String>();
		Connection con = new JDBCPostgresql().getconnect();
		try {
			Statement sql = con.createStatement();
			String sqlcmd = "select distinct caller_name from fsw_pstn_callin where conference_id='"+confid+"'";
			//System.out.println(sqlcmd);
			ResultSet res = sql.executeQuery(sqlcmd);
			//int i=0;
			while(res.next())
			{
				//i++;
				users.add(res.getString("caller_name"));
			}
			//System.out.println(i);
			res.close();
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sorry,there are some problems when connect to database!");
			users = null;
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	public String getmaxconid(){
		String maxconfid = null;
		Connection con = new JDBCPostgresql().getconnect();
		try {
			Statement sql = con.createStatement();
			String sqlcmd = "SELECT MAX(conference_id) FROM fsw_pstn_callin";
			ResultSet res = sql.executeQuery(sqlcmd);
			while(res.next()){
				maxconfid = res.getString(1);
			}
			//System.out.println(maxconfid);
			
			res.close();
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sorry,there are some problems when connect to database!");
			maxconfid=null;
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return maxconfid;
	}
	
	public List<Confid> getdbconfid(){
		List<Confid> confids = new ArrayList<Confid>();
		Connection con = new JDBCPostgresql().getconnect();
		try {
			Statement sql = con.createStatement();
			String sqlcmd = "select distinct conference_id from fsw_pstn_callin order by conference_id desc";
			ResultSet res = sql.executeQuery(sqlcmd);
			while(res.next())
			{
				confids.add(new Confid(res.getString("conference_id")));
			}
			res.close();
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sorry,there are some problems when connect to database!");
			confids=null;
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return confids;
	}
	
	public boolean insertdb(List<DbContent> contents){
		boolean isok = true;
		Connection con = new JDBCPostgresql().getconnect();
		try {
			Statement sql = con.createStatement();
			for(DbContent content : contents){
				String sqlcmd = "INSERT INTO fsw_pstn_callin(conference_id,caller_name,caller_password,uuid,isused) VALUES('"
								+content.getConference_id()+"','"
								+content.getCaller_name()+"','"
								+content.getCaller_password()+"','','false')";
				sql.executeUpdate(sqlcmd);
			}
			
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sorry,there are some problems when connect to database!");
			isok=false;
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isok;
	}
	
	public boolean updatedb(List<DbContent> contents){
		boolean isok = true;
		Connection con = new JDBCPostgresql().getconnect();
		try {
			Statement sql = con.createStatement();
			for(DbContent content : contents){
				String sqlcmd = "UPDATE user_tbl SET caller_name = "+content.getCaller_name()+" WHERE conference_id = "+content.getConference_id();
				sql.executeUpdate(sqlcmd);
			}
			
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sorry,there are some problems when connect to database!");
			isok=false;
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isok;
	}
	
	public boolean deletedb(String confid){
		boolean isok = true;
		Connection con = new JDBCPostgresql().getconnect();
		try {
			Statement sql = con.createStatement();
			String sqlcmd = "DELETE FROM fsw_pstn_callin WHERE conference_id = '"+confid+"'";
			sql.executeUpdate(sqlcmd);
			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sorry,there are some problems when connect to database!");
			isok=false;
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isok;
	}
	
}
