package com.yike.dbservlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yike.DAO.DbContent;
import com.yike.json.JsonOperation;

/**
 * Servlet implementation class Dbservlet
 */
@WebServlet("/Dbservlet")
public class Dbservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JsonOperation op = new JsonOperation();
		response.setContentType("text/javascript");
		String issubmit = request.getParameter("submit");
		String confiddialall = request.getParameter("confiddialall");
		if(issubmit!=null&&issubmit!=""&&issubmit.equals("true")){
			int usernum = Integer.parseInt(request.getParameter("usernum"));
			Map<String, ?> map = request.getParameterMap();
	        Iterator<String> iter = map.keySet().iterator();
	        int j =0;
	        List<String> jcontent = new ArrayList<String>();
	        while (iter.hasNext()) {
	        	if(j<4){
	        		iter.next();
	        		j++;
	        		continue;
	        	}	
	            String key = iter.next();
	            
	            String[] value =  (String[]) map.get(key);
	            for(String v:value){                
	            	jcontent.add(v);
	            } 
	        }
	        //System.out.println(jcontent.get(1));
			//String confid = request.getParameter("confidtext");
			//System.out.println(confid+"confid");
			List<DbContent> contents = new ArrayList<DbContent>();
			for(int i=0;i<usernum;i++){
				contents.add(new DbContent(null,jcontent.get(2*i),jcontent.get(2*i+1),"false"));
				//System.out.println("passwd_"+(i+1));
			}
			//System.out.println(request.getParameter("isupdate"));
			response.getWriter().print(op.tojsontext(contents,request.getParameter("isupdate")));
		}else if(confiddialall!=null&&confiddialall!=""){
			response.getWriter().print(op.tojsontext(confiddialall));
		}else{
			String confid = request.getParameter("confidquery");
			//System.out.println(confid);
			if(confid!=null&&confid!=""){
					response.getWriter().print(op.tojson("members",confid));
				}else
				response.getWriter().print(op.tojson("conference_id",null));
			}
		}
		
}
