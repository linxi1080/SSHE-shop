<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function loginUser() {
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/userAction!login.action',
			data : $('#user_login_loginForm').serialize(),
			dataType : 'json',
			beforeSend : function() {
				//验证数据
				return $('#user_login_loginForm').form('validate');
			},
			success : function(data) {
				//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
				//var obj = jQuery.parseJSON(data);
				//console.info(data);
				if (data.success) {
					$('#user_login_dialog').dialog('close');
				}
				// 消息将显示在顶部中间
				$.messager.show({
					title : '提示消息',
					msg : data.msg,
					showType : 'show'
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				/*
				$.messager.show({
					title : '提示消息',
					msg : XMLHttpRequest.status + ' ' + textStatus + XMLHttpRequest.responseText,
					showType : 'show'
				});
				*/
				$.messager.alert('错误',XMLHttpRequest.responseText,'error');
			}
		});
	}
	$(function(){
		$('#user_login_loginForm input').bind('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				loginUser();
			}
		});
		//input name=name的输入框获取焦点
		$('#user_login_loginForm input[name=name]').focus();
	});
</script>
<div id="user_login_dialog" class="easyui-dialog"
	data-options="title:'用户登录',iconCls:'ext-icon-lock_open',resizable:true,modal:true,closable: false,buttons:[{
				text:'注册',
				iconCls:'icon-edit',
				handler:function(){
					$('#user_reg_regform').form('load',{username:'',password:'',repassword:''});
					$('#user_reg_regdialog').dialog('open');
				}
			},{
				text:'登录',
				iconCls:'icon-ok',
				handler:function(){
					loginUser();
				}
			},{
				text:'重置',
				iconCls:'icon-redo',
				handler:function(){$('input').val('');}
			}]">
	<form id="user_login_loginForm" method="post">
		<table>
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">登录名：</th>
				<td ><input name="name" class="easyui-validatebox" value="admin" style="width:150px;"
					data-options="required:true, missingMessage:'请输入用户名'" /></td>
			</tr>
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">密 &nbsp; &nbsp;码：</th>
				<td><input name="pwd" value ="123456" type="password" style="width:150px;"
					class="easyui-validatebox"
					data-options="required:true, missingMessage:'请输入密码'" /></td>
			</tr>
		</table>
	</form>
</div>
