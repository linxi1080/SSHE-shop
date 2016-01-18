<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userRoleChart.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<script type="text/javascript">
	$(function() {
		$.messager.progress({
			text : '数据加载中....'
		});

		$('#jsp_admin_chart_userRoleChart_container').highcharts({
			exporting : {
				filename : '用户角色分布'
			},
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			title : {
				text : '用户角色分布'
			},
			tooltip : {
				pointFormat : '{series.name}: <b>{point.y}</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						color : '#000000',
						connectorColor : '#000000',
						formatter : function() {
							return '<b>' + this.point.name + '</b>角色：' + this.y + ' 人';
						}
					}
				}
			},
			series : [ {
				type : 'pie',
				name : '用户数量：',
				data : []
			} ]
		});
		$.post(lh.contextPath + '/roleAction!doNotNeedGrant_userRoleChart.action', function(result) {
			console.info(result);
			var trs = '';
			$.each(result, function(index, item) {
				trs += lh.formatString('<tr><td>{0}</td><td>{1}</td></tr>', item.name, item.y);
			});
			$('table tr td table').append(trs);

			var chart = $('#jsp_admin_chart_userRoleChart_container').highcharts();
			chart.series[0].setData(result);

			$.messager.progress('close');
		}, 'json');

	});
	</script>
    <table style="width: 100%; height: 100%">
		<tr>
			<td style="width: 60%"><div id="jsp_admin_chart_userRoleChart_container"></div></td>
			<td valign="top">
				<table class="table" style="margin-left: 20px;">
					<tr>
						<th>角色名称</th>
						<th>用户数量</th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
  </body>
</html>
