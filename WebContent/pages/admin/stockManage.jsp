<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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

	var MaxexportNum=0;
	$(function(){
		$("#dg").datagrid({
			onRowContextMenu:function(e,rowIndex,rowData){//右键时触发事件
			e.preventDefault();//阻止浏览器捕获右键事件
			$(this).datagrid("clearSelections");//取消所有选中项
			$(this).datagrid("selectRow",rowIndex);//根据索引选中该行
			$('#menu').menu('show',{
				//显示右键菜单
				left:e.pageX,//在鼠标点击处显示菜单
				top:e.pageY
			});
			e.preventDefault();//阻止浏览器自带的右键菜单弹出
			}
		});
		
		
		$.extend($.fn.validatebox.defaults.rules, {
		    validexportNum: {
				validator: function(value,param){
					return value <= MaxexportNum;
				},
				message: '出库数量超过库存!'
		    }
		});
	});


	var url;
	/*用formatter使field显示子属性  */
	function formatGoods(value,row,index){
		if(value==null){
			return ""; 
		}else{
			return row.goods.goodsName;
		}
	} 
	
	function searchStock(){
		$("#dg").datagrid('load',{
			"bimpoPrice":$("#s_bimpoPrice").val(),
			"eimpoPrice":$("#s_eimpoPrice").val(),
			"bexpoPrice":$("#s_bexpoPrice").val(),
			"eexpoPrice":$("#s_eexpoPrice").val(),
			"goodsName":$("#s_goodsName").val()
		});
	}
	
	
	function deleteStock(){
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
				$.post("${pageContext.request.contextPath}/stock/delete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert("系统提示",result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	function openStockAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加库存信息");
		url="${pageContext.request.contextPath}/stock/save";
	}
	
	function closeStockDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function closeexportDialog(){
		$("#dlg-export").dialog("close");
	}
	
	function resetValue(){
		$("#goodsId").combobox("setValue","");
		$("#stockNum").val("");
		$("#impoPrice").val("");
		$("#expoPrice").val("");
		$("#stockDesc").val("");
	}
	
	function saveStock(){
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
	
	
	function openStockModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要修改的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑库存信息");
		$("#goodsId").combobox("setValue",row.goodsId);
		$("#stockNum").val(row.stockNum);
		$("#limitNum").val(row.limitNum);
		$("#impoPrice").val(row.impoPrice);
		$("#expoPrice").val(row.expoPrice);
		$("#stockDesc").val(row.stockDesc);
		url="${pageContext.request.contextPath}/stock/save?id="+row.id;
	}
	
	function clearValue(){
		$("#s_bimpoPrice").val("");
		$("#s_eimpoPrice").val("");
		$("#s_bexpoPrice").val("");
		$("#s_eexpoPrice").val("");
		$("#s_goodsName").val("");
	}
	
	
	//打开出库窗口
	function exportPro(){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要修改的数据");
			return;
		}
		
		var row=selectedRows[0];
		$("#dlg-export").dialog("open").dialog("setTitle","设置出库信息");
		$("#exportId").combobox("select",row.goodsId);
		$("#export-stockId").val(row.id);
		$("#expoNum").val("0");
		$("#expoPrice-export").val(row.expoPrice);
		$("#expoDesc").val("");
		url="${pageContext.request.contextPath}/export/save";
	}
	
	function exportStock(){
		$("#fm-export").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval("("+result+")");
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return error;
				}else{
					$.messager.alert("系统提示","保存成功!");
					resetValue();
					$("#dlg-export").dialog("close");
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
</script>
</head>
<body style="margin: 5px;">
	<table style="height:423px; width:1160px" id="dg" title="库存管理" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/stock/list" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="15">编号</th>
				<th field="goodsId" width="15" hidden="true">商品编号</th>
				<th field="goods" width="15" formatter="formatGoods">商品名称</th>
				<th field="stockNum" width="25">商品库存数量</th>
				<th field="limitNum" width="25">最低库存限制</th>				
				<th field="impoPrice" width="25">成本价</th>
				<th field="expoPrice" width="25">销售价</th>
				<th field="stockDesc" width="100">库存备注</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<!-- <a href="javascript:openStockAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> -->
			<a href="javascript:openStockModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteStock()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;成本价格：&nbsp;<input type="text" name="bimpoPrice" id="s_bimpoPrice" size="10" />--<input type="text" name="eimpoPrice" id="s_eimpoPrice" size="10"/>
			&nbsp;销售价格：&nbsp;<input type="text" name="bexpoPrice" id="s_bexpoPrice" size="10" />--<input type="text" name="eexpoPrice" id="s_eexpoPrice" size="10"/>
			&nbsp;&nbsp;商品名称：&nbsp;<input type="text" name="goodsName" id="s_goodsName" size="10" />
			&nbsp;<a href="javascript:searchStock()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:clearValue()" class="easyui-linkbutton" iconCls="icon-no" plain="true">清空</a>
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:540px;height:350px;padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>商品名称：</td>
					<td><input class="easyui-combobox" id="goodsId" name="goodsId" size="10" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'goodsName',url:'${pageContext.request.contextPath}/goods/goodsComboList'"/></td>
				</tr>
				
				<tr>					
					<td>库存数量：</td>
					<td><input type="text" name="stockNum" id="stockNum" class="easyui-validatebox" required="true"/></td>
					<td>最低库存数量限制：</td>
					<td><input type="text" name="limitNum" id="limitNum" class="easyui-validatebox" required="true"/></td>
				</tr>
				
				<tr>
					<td>成本价：</td>
					<td><input  name="impoPrice" id="impoPrice" class="easyui-validatebox" required="true" /></td>
					
					<td>销售价：</td>
					<td><input  name="expoPrice" id="expoPrice" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td valign="top">库存备注：</td>
					<td colspan="3"><textarea rows="7" cols="43" name="stockDesc" id="stockDesc"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveStock()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeStockDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	
	<!-- 添加出库功能 -->
	<div id="dlg-export" class="easyui-dialog" style="width:540px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-buttons-export">
		<form id="fm-export" method="post">
			<input name="stockId" id="export-stockId" class="easyui-validatebox" hidden="false">
			<table cellspacing="5px;">
				<tr>
					<td>商品名称：</td>
					<td><input class="easyui-combobox" id="exportId" name="goodsId" size="10" data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'goodsName',url:'${pageContext.request.contextPath}/goods/goodsComboList'"/></td>
					
					<td>出库价格：</td>
					<td><input type="text" name="expoPrice" id="expoPrice-export" class="easyui-validatebox" required="true"/></td>
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
	
	<div id="dlg-buttons-export">
		<a href="javascript:exportStock()" class="easyui-linkbutton" iconCls="icon-ok">出库</a>
		<a href="javascript:closeexportDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	<!-- 添加菜单  -->
	<div id="menu" class="easyui-menu" style="width:30px;display:none;">
		<div id="btn_export" data-options="iconCls:'icon-add'" onclick="exportPro()">出货</div>
	</div>
</body>
</html>
