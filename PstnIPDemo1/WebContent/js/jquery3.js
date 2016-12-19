/**
 * 
 */
	$(function(){
		$("#createcall").click(function(){
			if(confirm("新建会议室？")){
				$("#newcaller").empty();
				$("#newcaller").append("<tr><td align='center'>会议室ID</td><td><input type='text' id='confid' readonly='readonly' name='confidtext' value='提交后自动生成'></td><td><button id='adduser'>增加用户</button></td></tr>");
				$("#newcaller").append("<table id='createmem' border='1' cellpadding='2' cellspacing='1' style='margin: 0 left'></table>");
				var passwd = ran(0,10000000,99999999);
				$("#createmem").append("<tr><td align='center'>主叫名称</td>"+
						"<td><input type='text' id='caller_1' class='caller' name='caller_1' " +
						/*"onkeyup='value=value.replace(/[\W]/g,"+"'') "+"' " +
						"onbeforepaste='clipboardData.setData("+"'text',clipboardData.getData('text').replace(/[^\d]/g,''))"+"' " +*/
						"maxlength=20></td>"+
						"<td align='center'>会议室密码</td>"+
						"<td><input type='text' id='passwd_1' class='passwd' readonly='readonly' name='passwd_1' value='"+passwd+"'></td>"+
						"<td><button id='delete_1' class='delete'>删除用户</button></td></tr>");
				$("#newcaller").append("<button id='submitbd'>提交</button>");
				return false;
			}
			return false;
		});
		
		$("#newcaller").on('click',".delete",function(){
			if($("#createmem").find("tr").length==1){
				alert("至少要有一个用户！");
				return false;
			}
			$(this).parent().parent().remove();
			var id = $(this).attr("id");
			var deletednum = id.substring(id.indexOf("_")+1);
			if(deletednum==$("#createmem").children(".delete").length){
				return false;
			}
			//alert($("#createmem").find("tr").length);
			for(var i=deletednum-1;i<$("#createmem").find("tr").length;i++){
				$("#createmem").find(".caller").eq(i).attr("id","caller_"+(i+1));
				$("#createmem").find(".passwd").eq(i).attr("id","passwd_"+(i+1));
				$("#createmem").find(".delete").eq(i).attr("id","delete_"+(i+1));
				//alert(i);
			}
			
			return fasle;
		})
		
		$("#newcaller").on('click',"#adduser",function(){
			var lastrownum = $("#createmem tr").length+1;
			var passwd = ran(0,10000000,99999999);
			//var passwd = "a";
			var row = $("<tr><td align='center'>主叫名称</td>"+
					"<td><input type='text' id='caller_"+lastrownum+"' class='caller' name='caller_"+lastrownum+"' " +
					/*"onkeyup='value=value.replace(/[\W]/g,&apos;&apos;) ' " +
						"onbeforepaste='clipboardData.setData(&apos;text&apos;,clipboardData.getData(&apos;text&apos;).replace(/[^\d]/g,&apos;&apos;))' " +*/
						"maxlength=20></td>"+
					"<td align='center'>会议室密码</td>"+
					"<td><input type='text' id='passwd_"+lastrownum+"' class='passwd' readonly='readonly' name='passwd_"+lastrownum+"' value='"+passwd+"'></td>"+
					"<td><button id='delete_"+lastrownum+"' class='delete'>删除用户</button></td></tr>");
			$("#createmem").append(row);
			return false;
		})
		
		$("#newcaller").on('click',"#submitbd",function(){
			for(var i =0;i<$("#createmem").find(".passwd").length;i++){
				var callname = $("#createmem").find(".caller").eq(i).val();
				if(callname==null||callname==""){
					alert("用户名称不能为空！");
					$("#createmem").find(".caller").eq(i).focus();
					return false;
				}
			}
			if(confirm("确认提交？每次提交后都会生成一个新的会议室ID")){
				var url = "Dbservlet";
				var jsoncontent = getjsoncontent();
				var args = {"time": new Date(),"submit":"true","usernum":$("#createmem tr").length,"abc":jsoncontent};
				
				$.post(url,args,function(data){
					//alert("FFFFFFFFFF");
					getack(data);
				},"json")
				return false;
			}
			return false;
		})
		
		/*
		$("#showdb").click(function(){
			if($("#showconfid").css("visibility")=="hidden"){
				$("#showconfid").css("visibility","visible");
				return false;
			}
			$("#showconfid").css("visibility","hidden");
			$("#showsingleconf").css("visibility","hidden");
			return false;
		});
		*/
		
		//.live('click',function()
		$("#showconfid").on('click',".single",function(){
			var url = "Dbservlet";
			$("#showconfid").find("td").each(function(){
				$(this).attr("style","background-color:#FFFFFF;");
			})
			$(this).parent().attr("style","background-color:#FFFF77;");
			//alert($(this).html());
			var args = {"time": new Date(),"confid":$(this).html()};
			
			$.post(url,args,function(data){
				getsingleconf(data);
			},"json")
			return false;
		});
		
		$("#showdb").click(function(){
			var url = "Dbservlet";
			var args = {"time": new Date()};
			$("#showconfid").attr("style","float: left;height:645px;overflow:auto;overflow-x: hidden;");
			
			$.post(url,args,function(data){
				getallconfid(data);
			},"json")
			return false;
		});
	})
	
	function getsingleconf(data){
		//<table border="1" cellpadding="2" cellspacing="1" style="margin: 0 auto">
		$("#showsingleconf").empty();
		$("#showsingleconf").attr("style","float: left;height:645px;overflow:auto;overflow-x: hidden;");
		$("#showsingleconf").append("<table id='showsingletable' border='1' cellpadding='2' cellspacing='1' style='background-color:#FFFF77;margin: 0 auto'></table>");
		$("#showsingletable").append("<tr><td align='center'>会议室ID</td>"+
				"<td align='center'>主叫名称</td>"+
				"<td align='center'>会议室密码</td>"+
				"<td align='center'>是否已接入</td></tr>");
		//alert("what wrong");
		$(data).each(function(){
			$("#showsingletable").append("<tr><td align='center'>"+this.conference_id +"</td>"+
					"<td align='center'>"+this.caller_name+"</td>"+
					"<td align='center'>"+this.caller_password+"</td>"+
					"<td align='center'>"+(this.isused=="true"?"是":"否")+"</td></tr>");
		})
	}
	
	function getallconfid(data){
		$("#showconfid").empty();
		$("#showsingleconf").empty();
		$("#showconfid").append("<table id='showtable' border='1' cellpadding='2' cellspacing='1' style='margin: 0 left'></table>");
		$("#showtable").append("<tr><td align='center'>会议室id</td></tr>");
		$(data).each(function(){
			$("#showtable").append("<tr><td align='center'>"+"<a href='Dbservlet' class='single'>"+this.confid+"</a>"+"</td></tr>");
		})
	}
	
	function getjsoncontent(){
		var jsonArr = new Array();   
		for(var i =0;i<$("#createmem").find(".passwd").length;i++){                 
          var jsonObj = {};
          var caller = $("#createmem").find(".caller").eq(i).val();
          var passwd = $("#createmem").find(".passwd").eq(i).val();
          jsonObj.caller_name=caller;
          jsonObj.caller_password=passwd;
          jsonArr.push(jsonObj);           
        }
		return jsonArr;
	}
	
	function getack(data){
		if(this.confid=="false"){
			alert("系统异常，提交失败！")
		}else{
			//var confid = this.confidnow;
			//alert(data);
			//$("#confid").val(confid);
			alert("提交成功！");
		}
	}
	
	function ran(n,min,max){
		 var arr=parseInt(Math.random()*(max-min+1)+min);
		 if($("#createmem tr")!=null){
			 for(var i=n;i<$("#createmem tr").length;i++){
				 var exist = $("#createmem").find(".passwd").eq(i).val();
				 if(arr==exist){
					 return ran(i,min,max);
				 }
			 } 
		 } 
		 return arr;
	}