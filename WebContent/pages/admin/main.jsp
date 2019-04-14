<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
  //拼接网址http://localhost:8080/StockManageSystem_ssm/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>库存管理系统</title>
<base href="<%=basePath%>">
<%
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect(basePath);
		return;
	}
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	var ctx="${pageContext.request.contextPath}";
	//等效于$(document).ready(function(){...});是简写 
	$(function(){
		var treeData=[
		    {
		    	text:"库存管理",
		    	children:[{
		    		text:"供应商管理",
		    		attributes:{
		    			url:ctx+"/pages/admin/providerManage.jsp"
		    		}
		    	},{
		    		text:"商品类别管理",
		    		attributes:{
		    			url:ctx+"/pages/admin/goodsTypeManage.jsp"
		    		}
		    	},{
		    		text:"商品管理",
		    		attributes:{
		    			url:ctx+"/pages/admin/goodsManage.jsp"
		    		}
		    	},{
		    		text:"商品入库管理",
		    		attributes:{
		    			url:ctx+"/pages/admin/importManage.jsp"
		    		}
		    	},{
		    		text:"商品库存管理",
		    		attributes:{
		    			url:ctx+"/pages/admin/stockManage.jsp"
		    		}
		    	},{
		    		text:"商品出库记录管理",
		    		attributes:{
		    			url:ctx+"/pages/admin/exportManage.jsp"
		    		}
		    	},{
		    		text:"销售利润统计",
		    		attributes:{
		    			url:ctx+"/pages/admin/profitManage.jsp"
		    		}
		    	},{
		    		text:"库存预警查询",
		    		attributes:{
		    			url:ctx+"/pages/admin/warningManage.jsp"
		    		}
		    	},{
		    		text:"保质期报警",
		    		attributes:{
		    			url:ctx+"/pages/admin/timeoutManage.jsp"
		    		}
		    	}]
		    }
		              ];
		
		$("#tree").tree({
			data:treeData,
			lines:true,
			onClick:function(node){
				if(node.attributes){
					openTab(node.text,node.attributes.url);
				}
			}
		});
	
	function openTab(text,url){
		var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
		if($("#tabs").tabs('exists',text)){
			$("#tabs").tabs('select',text);
			var current_tab=$('#tabs').tabs('getSelected');
			$('#tabs').tabs('update',{
				tab:current_tab,
				options:{
					content:content
				}
			});
		}else{
			$("#tabs").tabs('add',{
				title:text,
				closable:true,
				content:content
			});
		}
	}
});
</script>
</head>
<body class="easyui-layout">
	<div region="north" style="height:80px;background-color:#105d95;">
		<div align="left" style="width:80%;float:left"><img src="images/main.jpg"></div>
		<div style="padding-top:10px;padding-right:20px;">当前用户：<font color="red" size="10px">${currentUser.userName }</font>
			<a href="user/logOut">&gt;&gt;<font color="red">注销</font></a>
		</div>
	</div>
	
	<div region="west" style="width:200px;" title="导航菜单" split="true">
		<ul id="tree">
		
		</ul>
	</div>
	
	<div region="center" class="easyui-tabs" fit="true" border="false" id="tabs">
		<div title="首页">
			<div align="" style="padding-left:100px;padding-top:100px;"><font color="red" size="10">欢迎使用库存管理系统</font></div>
		</div>
	</div>
</body>
</html>