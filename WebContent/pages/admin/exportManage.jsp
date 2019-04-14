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
	
	/*用formatter使field显示子属性  */
	function formatGoods(value,row,index){
		if(value==null){
			return ""; 
		}else{
			return row.goods.goodsName;
		}
	} 
	
	
	function searchExport(){
		$("#dg").datagrid('load',{
			"goodsName":$("#s_goodsName").val(),
			"bexpoPrice":$("#s_bexpoPrice").val(),
			"eexpoPrice":$("#s_eexpoPrice").val(),
			"bexpoDate":$("#s_bexpoDate").datebox("getValue"),
			"eexpoDate":$("#s_eexpoDate").datebox("getValue")
		});
	}
	
	
	function deleteExport(){
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据");
			return;
		}
		
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗?",function(r){
			if(r){
				$.post("${pageContext.request.contextPath}/export/delete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示","<font color=red>"+selectedRows[result.errorIndex].goodsName+"</font>"+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	function openExportAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加入库信息");
		url="${pageContext.request.contextPath}/export/save";
	}
	
	function saveExport(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval("("+result+")");
				if(result.success){
					$.messager.alert("系统提示","保存成功！");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示","保存失败！");
					return;
				}
			}
		});
	}
	
	function resetValue(){
		$("#goodsId").combobox("setValue","");
		$("#expoPrice").val("");
		$("#expoDate").datebox("setValue","");
		$("#expoNum").val("");
		$("#expoDesc").val("");
	}
	
	function closeExportDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function openExportModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要修改的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑出库信息");
		$("#goodsId").combobox("setValue",row.goodsId);
		$("#expoPrice").val(row.expoPrice);
		$("#expoNum").val(row.expoNum);
		$("#expoDesc").val(row.expoDesc);
		$("#expoDate").datebox("setValue",row.expoDate);
		url="${pageContext.request.contextPath}/export/save?id="+row.id;
	}
	
	function clearValue(){
		$("#s_bexpoPrice").val("");
		$("#s_eexpoPrice").val("");
		$("#s_bexpoDate").datebox("setValue","");
		$("#s_eexpoDate").datebox("setValue","");
		$("#s_goodsName").val("");
	}
	
	function exportData(){
		window.open('${pageContext.request.contextPath}/export/export')
	}
	
</script>
</head>
<body style="margin: 5px;">
	<table style="height:423px; width:1160px;" id="dg" title="商品出库管理" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/export/list" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="20">编号</th>
				<th field="goodsId" width="20" hidden="true">商品ID</th>
				<th field="goods" width="50" formatter="formatGoods">商品名称</th>
				<th field="expoPrice" width="30">销售价格</th>
				<th field="expoDate" width="60">出库时间</th>
				<th field="expoNum" width="60">出库数量</th>
				<th field="expoDesc" width="100">出库备注</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<!-- <a href="javascript:openExportAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> -->
			<a href="javascript:openExportModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteExport()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="exportData()">导出数据</a>
		</div>
		<div>
			&nbsp;出库价格：&nbsp;<input type="text" name="bexpoPrice" id="s_bexpoPrice" size="10" />--<input type="text" name="eexpoPrice" id="s_eexpoPrice" size="10"/>
			&nbsp;出库时间：&nbsp;<input class="easyui-datebox" name="bexpoDate" id="s_bexpoDate" editable="false" size="10" />-><input class="easyui-datebox" name="eexpoDate" id="s_eexpoDate" editable="false" size="10" />
			&nbsp;&nbsp;商品名称：&nbsp;<input type="text" name="goodsName" id="s_goodsName" size="10" />
			&nbsp;<a href="javascript:searchExport()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:clearValue()" class="easyui-linkbutton" iconCls="icon-no" plain="true">清空</a>
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:540px;height:350px;padding: 10px 20px"
	closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>商品名称：</td>
					<td><input class="easyui-combobox" id="goodsId" name="goodsId" size="10" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'goodsName',url:'${pageContext.request.contextPath}/goods/goodsComboList'"/></td>
					
					<td>出库价格：</td>
					<td><input type="text" name="expoPrice" id="expoPrice" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>出库日期：</td>
					<td><input class="easyui-datebox" name="expoDate" id="expoDate" editable="false" size="15" required="true"/>
					
					<td>出库数量：</td>
					<td><input  name="expoNum" id="expoNum" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td valign="top">出库备注：</td>
					<td colspan="3"><textarea rows="7" cols="43" name="expoDesc" id="expoDesc"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveExport()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeExportDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	
</body>
</html>
