<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function regUser() {
		/*
		$('#user_reg_regform').form('submit', {
			url : '${pageContext.request.contextPath}/userAction!reg.action',
			//onSubmit: function(){    
			// do some check    
			// return false to prevent submit;    
			//},    
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.sucess) {
					$('#user_reg_regdialog').dialog('close');
				}
				// 消息将显示在顶部中间
				$.messager.show({
					title : '提示消息',
					msg : obj.msg,
					showType : 'show'
				});
			}
		});
		 */
		$.ajax({
			type : 'POST',
			url : '${pageContext.request.contextPath}/userAction!reg.action',
			data : $('#user_reg_regform').serialize(),
			/*
			data :{
				username:$('#user_reg_regform input[name=username]').val(),
				password:$('#user_reg_regform input[name=password]').val(),
				repassword:$('#user_reg_regform input[name=repassword]').val()
				//异步传入的数据
			},
			*/
			dataType:'json',
			beforeSend : function() {
				//验证数据
				return $('#user_reg_regform').form('validate');
				//$('#dg').datagrid('loading');//显示进度提示信息
			},
			success : function(data) {
				//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
				//var obj = jQuery.parseJSON(data);
				if (data.success) {
					$('#user_reg_regdialog').dialog('close');
				}
				// 消息将显示在顶部中间
				$.messager.show({
					title : '提示消息',
					msg : data.msg,
					showType : 'show'
				});
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				/*
				console.info(XMLHttpRequest);
				console.info(textStatus);
				console.info(errorThrown);
				*/
				$.messager.show({
					title : '提示消息',
					msg : XMLHttpRequest.status+' '+textStatus,
					showType : 'show'
				});
			}
		});
	}
	$(function(){
		$('#user_reg_regform input').bind('keyup', function(event) {/* 增加回车提交功能 */
			if (event.keyCode == '13') {
				//$('#user_reg_regform').submit();
				regUser();
			}
		});
	});
</script>
<div id="user_reg_regdialog" class="easyui-dialog"
	data-options="title:'用户注册',iconCls:'icon-save',closed:true,resizable:true,buttons:[{
				text:'注册',
				iconCls:'icon-edit',
				handler:function(){
					// submit the form    
					//$('#user_reg_regform').submit();
					regUser(); 
				}
			}]">
	<form id="user_reg_regform" method="post">
		<table style="margin-top:8px">
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">登 &nbsp;录 &nbsp;名：</th>
				<td style="font-size:14px;font-family: 微软雅黑"><input
					style="width:150px;" name="username" class="easyui-validatebox"
					data-options="required:true, missingMessage:'请输入用户名'" /></td>
			</tr>
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">密 &nbsp; &nbsp;
					&nbsp;&nbsp;码：</th>
				<td><input style="width:150px;" id="user_reg_password" type="password" name="password"
					class="easyui-validatebox"
					data-options="required:true, missingMessage:'请输入密码'" /></td>
			</tr>
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">确认密码：</th>
				<td><input style="width:150px;" type="password" name="repassword"
					class="easyui-validatebox"
					data-options="required:true ,missingMessage:'请确认密码', validType:'eqPwd[\'#user_reg_password\']'" />
				</td>
			</tr>
		</table>
	</form>
</div>
