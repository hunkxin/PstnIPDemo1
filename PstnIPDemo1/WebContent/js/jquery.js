/**
 * 
 */
	$(function(){
		$("#createcall").click(function(){
			if(confirm("新建会议室？")){
				$("#newcaller").empty();
				$("#newcaller").append("<tr><td align='center'>会议室ID</td><td><input type='text' id='confid' readonly='readonly' name='confidtext' value='提交后自动生成'></td><td><button id='adduser'>增加用户</button></td></tr>");
				$("#newcaller").append("<table id='createmem' border='1' cellpadding='2' cellspacing='1' style='margin: 0 left'></table>");
				//var passwd = ran(0,10000000,99999999);
				$("#createmem").append("<tr><td align='center'>主叫名称</td>"+
						"<td><input type='text' id='caller_1' class='caller' name='caller_1' " +
						/*"onkeyup='value=value.replace(/[\W]/g,"+"'') "+"' " +
						"onbeforepaste='clipboardData.setData("+"'text',clipboardData.getData('text').replace(/[^\d]/g,''))"+"' " +*/
						"maxlength=20></td>"+
						"<td align='center'>会议室密码</td>"+
						"<td><input type='text' id='passwd_1' class='passwd' readonly='readonly' name='passwd_1' value='提交后自动生成'></td>"+
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
			//var passwd = ran(0,10000000,99999999);
			//var passwd = "a";
			var row = $("<tr><td align='center'>主叫名称</td>"+
					"<td><input type='text' id='caller_"+lastrownum+"' class='caller' name='caller_"+lastrownum+"' " +
					/*"onkeyup='value=value.replace(/[\W]/g,&apos;&apos;) ' " +
						"onbeforepaste='clipboardData.setData(&apos;text&apos;,clipboardData.getData(&apos;text&apos;).replace(/[^\d]/g,&apos;&apos;))' " +*/
						"maxlength=20></td>"+
					"<td align='center'>会议室密码</td>"+
					"<td><input type='text' id='passwd_"+lastrownum+"' class='passwd' readonly='readonly' name='passwd_"+lastrownum+"' value='提交后自动生成'></td>"+
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
			if(confirm("确认提交？")){
				var url = "Dbservlet";
				for(var i =0;i<$("#createmem").find(".passwd").length;i++){
					$("#createmem").find(".passwd").eq(i).val(ran(0,10000000,99999999));
				}
				var confupdate = $("#confid").val()=="提交后自动生成"?"newid":$("#confid").val();
				//alert(confupdate);
				var jsoncontent = getjsoncontent();
				var args = {"time": new Date(),"submit":"true","usernum":$("#createmem tr").length,"isupdate":confupdate,"abc":jsoncontent};
				
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
			$(this).parent().attr("style","background-color:#FFFF77;width:110px");
			//alert($(this).html());
			var args = {"time": new Date(),"confidquery":$(this).html()};
			
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
		
		$("#dailall").on('click',"#btdialall",function(){
			var url = "Dbservlet";
			//alert($("#showsingletable").find(".singleconfid").eq(0).text());
			var args = {"time": new Date(),"confiddialall":$("#showsingletable").find(".singleconfid").eq(0).text()};
			$.post(url,args,function(data){
				
			},"json")
			alert("正在呼叫所有人员进入会议室，请耐心等待！");
			return false;
		});
	})
	
	function getsingleconf(data){
		//<table border="1" cellpadding="2" cellspacing="1" style="margin: 0 auto">
		$("#showsingleconf").empty();
		if($("#btdialall").length==0){
			$("#dailall").empty();
			$("#dailall").attr("style","float: left;");
			$("#dailall").append("<button id='btdialall'>一键拨打所有人</button>")
		}
		
		$("#showsingleconf").attr("style","float: left;height:645px;overflow:auto;overflow-x: hidden;");
		$("#showsingleconf").append("<table id='showsingletable' border='1' cellpadding='2' cellspacing='1' style='background-color:#FFFF77;margin: 0 auto'></table>");
		$("#showsingletable").append("<tr><td align='center'>会议室ID</td>"+
				"<td align='center'>主叫名称</td>"+
				"<td align='center'>会议室密码</td>"+
				"<td align='center'>是否已接入</td></tr>");
		//alert("what wrong");
		$(data).each(function(){
			$("#showsingletable").append("<tr><td align='center' class='singleconfid'>"+this.conference_id +"</td>"+
					"<td align='center'>"+this.caller_name+"</td>"+
					"<td align='center'>"+this.caller_password+"</td>"+
					"<td align='center'>"+(this.isused=="true"?"是":"否")+"</td></tr>");
		})
		
	}
	
	function getallconfid(data){
		$("#showconfid").empty();
		$("#showsingleconf").empty();
		$("#dailall").empty();
		$("#showconfid").append("<table id='showtable' border='1' cellpadding='2' cellspacing='1' style='margin: 0 left'></table>");
		$("#showtable").append("<tr><td align='center'>会议室id</td></tr>");
		$(data).each(function(){
			$("#showtable").append("<tr><td align='center' style='width:110px'>"+"<a href='Dbservlet' class='single'>"+this.confid+"</a>"+"</td></tr>");
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
		if(data.confid=="0"){
			alert("系统异常，提交失败！")
		}else{
			//var confid = this.confid;
			//alert(data.confid);
			$("#confid").val(data.confid);
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