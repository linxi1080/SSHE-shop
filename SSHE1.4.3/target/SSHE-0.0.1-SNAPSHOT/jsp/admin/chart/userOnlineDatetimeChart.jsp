<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'onlineCreateDatetimeChart.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@ include file="../../public/commons.jspf"%>
  </head>
  
  <body>
  <script type="text/javascript">
	$(function() {
		$.messager.progress({
			text : '数据加载中....'
		});
		/*
		$('#jsp_admin_chart_onlineCreateDatetime_container').highcharts({
			exporting : {
				filename : '用户登录时间分布'
			},
			chart : {
				type : 'column',
				margin : [ 50, 50, 100, 80 ]
			},
			title : {
				text : '用户登录时间(总用户数：0)'
			},
			xAxis : {
				categories : [ '00-02', '02-04', '04-06', '06-08', '08-10', '10-12', '12-14', '14-16', '16-18', '18-20', '20-22', '22-24' ],
				labels : {
					rotation : -45,
					align : 'right',
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				}
			},
			yAxis : {
				min : 0,
				title : {
					text : '时间段用户登录数'
				}
			},
			legend : {
				enabled : false
			},
			tooltip : {
				formatter : function() {
					return '<b>' + this.x + '点</b><br/>' + '此时间段用户登录数量为: ' + this.y + '个用户';
				}
			},
			series : [ {
				data : [],
				dataLabels : {
					enabled : true,
					rotation : -90,
					color : '#FFFFFF',
					align : 'right',
					x : 4,
					y : 10,
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				}
			} ]
		});
		*/
		$('#jsp_admin_chart_onlineCreateDatetime_container').highcharts({
			exporting : {
				filename : '用户登录时间分布'
			},
			chart : {
				type : 'line',
				margin : [ 50, 50, 100, 80 ]
			},
			title : {
				text : '用户登录时间(总用户数：0)',
					x: -20 //center
			},
			xAxis : {
				categories : [ '00-02', '02-04', '04-06', '06-08', '08-10', '10-12', '12-14', '14-16', '16-18', '18-20', '20-22', '22-24' ],
				labels : {
					rotation : -45,
					align : 'right',
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				}
			},
			yAxis : {
				min : 0,
				title : {
					text : '时间段用户登录数'
				}
			},
			legend: {
				enabled : false
	        },
			tooltip : {
				formatter : function() {
					return '<b>' + this.x + '点</b><br/>' + '此时间段用户登录数量为: ' + this.y + '个用户';
				}
			},
			series : [ {
				data : [],
				dataLabels : {
					enabled : true,
					rotation : -90,
					color : '#FFFFFF',
					align : 'right',
					x : 4,
					y : 10,
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				}
			} ]
		});
		$.post(lh.contextPath + '/onlineAction!doNotNeedGrant_onlineCreateDatetimeChart.action', function(result) {
			$.each(result, function(index, item) {
				//$('table tbody tr td:eq(' + index + ')').html(item);
				$('#jsp_admin_chart_onlineCreateDatetime_table tbody tr td:eq(' + index + ')').html(item);
			});

			var chart = $('#jsp_admin_chart_onlineCreateDatetime_container').highcharts();
			console.info(result);
			chart.series[0].setData(result);
			var countUser = 0;
			for (var i = 0; i < result.length; i++) {
				countUser += result[i];
			}
			chart.setTitle({
				text : '用户登录时间(总用户数：' + countUser + ')'
			});
			$.messager.progress('close');
		}, 'json');

	});
</script>
    <div id="jsp_admin_chart_onlineCreateDatetime_container"></div>
	<div align="center">
		<table id="jsp_admin_chart_onlineCreateDatetime_table" class="table" style="font-size: x-small;">
			<thead>
				<tr>
					<th>00点-02点</th>
					<th>02点-04点</th>
					<th>04点-06点</th>
					<th>06点-08点</th>
					<th>08点-10点</th>
					<th>10点-12点</th>
					<th>12点-14点</th>
					<th>14点-16点</th>
					<th>16点-18点</th>
					<th>18点-20点</th>
					<th>20点-22点</th>
					<th>22点-24点</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
  </body>
</html>
