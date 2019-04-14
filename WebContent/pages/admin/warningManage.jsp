<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>库存管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	$(function(){
		$("#dg").datagrid({
			rowStyler:function(index,row){
				if(row.stockNum<row.limitNum){
					return 'color:red';
				}
			}
		});
	});
	
	
	
	function search(){
		$("#dg").datagrid('load',{
			"goodsName":$("#s_goodsName").val(),
		});
	}
	
	function clearValue(){
		$("#s_goodsName").val("");
	}
	
	function exportData(){
		window.open('${pageContext.request.contextPath}/warn/export')
	}
	
</script>
</head>
<body style="margin: 5px;">
	<table style="height:auto; width:1160px;" id="dg" title="库存预警查询" class="easyui-datagrid" fitColumns="true"
	 pagination="true" showFooter="true" rownumbers="true" url="${pageContext.request.contextPath}/warn/list" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="15">编号</th>
				<th field="goodsId" width="15">商品编号</th>
				<th field="goodsName" width="15">商品名称</th>
				<th field="stockNum" width="25">商品库存数量</th>
				<th field="limitNum" width="25">商品最低库存数量限制</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="exportData()">导出数据</a>
		</div>
		<div>
			&nbsp;&nbsp;商品名称：&nbsp;<input type="text" name="goodsName" id="s_goodsName" size="10" />
			&nbsp;<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:clearValue()" class="easyui-linkbutton" iconCls="icon-no" plain="true">清空</a>
		</div>
	</div>
	
</body>
</html>
