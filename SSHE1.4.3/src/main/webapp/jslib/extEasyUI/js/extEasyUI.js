var lh = lh || {};
// 格式化日期
Date.prototype.format = function(format) {
	if(isNaN(this.getMonth())){
		return '';
	}
	if(!format){
		formate="yyyy-MM-dd hh:mm:ss";
	}
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};
// 格式化日期
// 格式："yyyy-MM-dd hh:mm:ss"
function formatDatebox(value,format) {
	if(!format){
		format="yyyy-MM-dd hh:mm:ss";
	}
	if (value == null || value == '') {
		return '';
	}
	var dt;
	if (value instanceof Date) {
		dt = value;
	} else {
		dt = new Date(value);
		if(!dt.valueOf()){
			//转换为日期格式
			dt = parseDate(value);
		}
	}
	return dt.format(format); // 扩展的Date的format方法(上述插件实现)
}
/**
 * 把字符串转换为日期的方法
 * @param str
 * @returns {Date}
 */
function parseDate(str) {
	// 这个函数用来把字符串转换为日期格式
	// "2010-04-15 12:34:00"
	// "2010/04/15 12:34:00"
	return new Date(Date.parse(str.replace(/-/g,"/")));
}

/**
 * EasyUI更换主题的方法
 */
changeTheme = function(themeName){
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	console.info(url);
	var href = url.substring(0,url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	console.info(href);
	$easyuiTheme.attr('href',href);
	console.info($easyuiTheme.attr('href'));
	var $iframe = $('iframe');
	if($iframe.length > 0){
		for(var i = 0;i<$iframe.length;i++){
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href',href);
		}
	}
	//使用javascript设置Cookie值
	setCookie('easyuiThemeName',themeName);
	//需要使用Jquery.cookie.js这里没有下载故注释掉
//	$.cookie('easyuiThemeName',themeName,{expires:7});
};
/**
 * 打开tabs
 * 使用了ifrome方式
 * @param tabsId tabsid
 * @param rightMenuId  右键菜单id
 * @param options  tabs options
 */
function addTabiFrame(tabsId,rightMenuId,options){
	var tabs = $('#'+tabsId);
	var _home = '主页';
	if (tabs.tabs('exists', options.title)){
		tabs.tabs('select', options.title);//选中并刷新
		var currTab = tabs.tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != _home) {
			tabs.tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			});
		}
	} else {
		var content = createFrame(options.href);
		tabs.tabs('add',{
			title:options.title,
			content:content,
			closable:options.title != _home?true:false,
			iconCls:options.iconCls
		});
	}
	tabClose(tabsId,rightMenuId);
}
/**
 * 使用href方式打开tabs
 * 推荐使用该方法
 * @param tabsId
 * @rightMenuId  rightMenuId 右键菜单id
 * @param title
 * @param url
 * @param icon
 */
function addTab(tabsId,rightMenuId,options){
	//title, url,icon
	var tabs = $('#'+tabsId);
	//主页title
	var _home = '主页';
	if (tabs.tabs('exists', options.title)){
		tabs.tabs('select', options.title);//选中并刷新
		var currTab = tabs.tabs('getSelected');
		if(options.href != undefined && currTab.panel('options').title != _home) {
			tabs.tabs('update',{
				tab:currTab,
				options:{
					href:options.href
				}
			});
		}
	} else {
		tabs.tabs('add',{
			title:options.title,
			href:options.href,
			closable:options.title != _home?true:false,
			iconCls:options.iconCls
		});
	}
	tabClose(tabsId,rightMenuId);
}
//function addTab(title,url,icon){
//	if(!$('#tabs').tabs('exists',title)){
//		$('#tabs').tabs('add',{
//			title:title,
//			content:createFrame(url),
//			closable:true,
//			icon:icon
//		});
//	}else{
//		$('#tabs').tabs('select',title);
//	}
//	tabClose();
//}
/**
 * 把url转换成iframe内容
 */
function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}
/**
 * 双击关闭TAB页
 * @tabsId 双击的tabs的id
 * @rightMenuId 右键菜单ID
 */
function tabClose(tabsId,rightMenuId) {
	/*双击关闭TAB选项卡*/
	var tabs = $('#'+tabsId);
	var _rightMenuId = $('#'+rightMenuId);
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		tabs.tabs('close',subtitle);
	});
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		_rightMenuId.menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();
		_rightMenuId.data("currtab",subtitle);
		tabs.tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
/**
 * 鼠标右键菜单
 * tabRigthMenuEven
 * @id easy-tabs的id，不包含#
 * @rightMenuId  右键菜单ID
 * #mm-tabupdate,#mm-tabclose,#mm-tabcloseall,mm-tabcloseother,#mm-tabcloseright,#mm-tabcloseleft,#mm-exit
 */
function tabRigthMenuEven(tabsId,rightMenuId) {
	var tabs = $('#'+tabsId);
	var _rightMenuId = $('#'+rightMenuId);
	//主页title
	var _home = '主页';
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = tabs.tabs('getSelected');
		var content = $(currTab.panel('options').content).attr('src');
		var url = currTab.panel('options').href;
		//href刷新
		if(url != undefined && currTab.panel('options').title != _home) {
			tabs.tabs('update',{
				tab:currTab,
				options:{
					href:url
				}
			});
		}
		//iframe 刷新
		if (content != undefined && currTab.panel('options').title != _home){
			tabs.tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(content)
				}
			});
		}
	});
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = _rightMenuId.data("currtab");
		tabs.tabs('close',currtab_title);
	});
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t != _home) {
				tabs.tabs('close',t);
			}
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();		
		if(prevall.length>0){
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != _home) {
					tabs.tabs('close',t);
				}
			});
		}
		if(nextall.length>0) {
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != _home) {
					tabs.tabs('close',t);
				}
			});
		}
		return false;
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			tabs.tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			tabs.tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		_rightMenuId.menu('hide');
	});
}
/**
 * 设置Cookie值
 * //两个参数，一个是cookie的名子，一个是值
 * @param name
 * @param value
 */
function setCookie(name,value) {//两个参数，一个是cookie的名子，一个是值
    var Days = 30; //此 cookie 将被保存 30 天
    var exp = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
/**
 * 取Cookie函数
 * @param name
 * @returns
 */
function getCookie(name) {//取cookies函数        
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;
}
/**
 * EasyUI扩展js文件请在EasyUI的js文件加载后再加载
 */
/**
 * @author 林夕，linxi1080
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});
/**
 * EasyUI加载错误提示扩展
 * @param XMLHttpRequest
 * @returns
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	/* $.messager.progress('close'); */
	/* alert(XMLHttpRequest.responseText.split('<script')[0]); */
	$.messager.alert('错误', XMLHttpRequest.responseText.split('<script')[0]);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;
/**
 * dataGrid列移动扩展
 */
$.extend($.fn.datagrid.methods,{
	columnMoving: function(jq){
		return jq.each(function(){
			var target = this;
			var cells = $(this).datagrid('getPanel').find('div.datagrid-header td[field]');
			//移动出列图标
			var column_move_start = "column_move_start";
			//在列内移动图标
//			var column_move_end = "tree-dnd-no";
			var column_move_end = "column_move_end";
			cells.draggable({
				revert:true,
				cursor:'pointer',
				edge:5,
				proxy:function(source){
//					var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000"/>').appendTo('body');
					var p = $('<div class="tree-node-proxy '+ column_move_end + '" style="position:absolute;border:1px solid #ff0000"/>').appendTo('body');
					p.html($(source).text());
					p.hide();
					return p;
				},
				onBeforeDrag:function(e){
					e.data.startLeft = $(this).offset().left;
					e.data.startTop = $(this).offset().top;
				},
				onStartDrag: function(){
					$(this).draggable('proxy').css({
						left:-10000,
						top:-10000
					});
				},
				onDrag:function(e){
					$(this).draggable('proxy').show().css({
						left:e.pageX+15,
						top:e.pageY+15
					});
					return false;
				}
			}).droppable({
				accept:'td[field]',
				onDragOver:function(e,source){
//					$(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
					$(source).draggable('proxy').removeClass(column_move_end).addClass(column_move_start);
					$(this).css('border-left','1px solid #ff0000');
				},
				onDragLeave:function(e,source){
					$(source).draggable('proxy').removeClass(column_move_start).addClass(column_move_end);
					$(this).css('border-left',0);
				},
				onDrop:function(e,source){
					$(this).css('border-left',0);
					var fromField = $(source).attr('field');
					var toField = $(this).attr('field');
					setTimeout(function(){
						moveField(fromField,toField);
						$(target).datagrid();
						$(target).datagrid('columnMoving');
					},0);
				}
			});
			
			// move field to another location
			function moveField(from,to){
				var columns = $(target).datagrid('options').columns;
				var cc = columns[0];
				var c = _remove(from);
				if (c){
					_insert(to,c);
				}
				
				function _remove(field){
					for(var i=0; i<cc.length; i++){
						if (cc[i].field == field){
							var c = cc[i];
							cc.splice(i,1);
							return c;
						}
					}
					return null;
				}
				function _insert(field,c){
					var newcc = [];
					for(var i=0; i<cc.length; i++){
						if (cc[i].field == field){
							newcc.push(c);
						}
						newcc.push(cc[i]);
					}
					columns[0] = newcc;
				}
			}
		});
	}
});
/** 
 * @author 孙宇 
 *  
 * @requires jQuery,EasyUI 
 *  
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中 
 * 现在注释掉使用流云的扩展库
 */  
/*
var createGridHeaderContextMenu = function(e, field) {  
    e.preventDefault();  
    var grid = $(this);//grid本身 
    var headerContextMenu = this.headerContextMenu;// grid上的列头菜单对象   
    if (!headerContextMenu) {  
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');  
        var fields = grid.datagrid('getColumnFields');  
        for ( var i = 0; i < fields.length; i++) {  
            var fildOption = grid.datagrid('getColumnOption', fields[i]);  
            if (!fildOption.hidden) {  
                $('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);  
            } else {  
                $('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);  
            }  
        }  
        headerContextMenu = this.headerContextMenu = tmenu.menu({  
            onClick : function(item) {  
                var field = $(item.target).attr('field');  
                console.info("========");
                if (item.iconCls == 'icon-ok') {  
                    grid.datagrid('hideColumn', field);  
                    $(this).menu('setIcon', {  
                        target : item.target,  
                        iconCls : 'icon-empty'  
                    });  
                } else {  
                    grid.datagrid('showColumn', field);  
                    $(this).menu('setIcon', {  
                        target : item.target,  
                        iconCls : 'icon-ok'  
                    });  
                }  
            }  
        });  
    }  
    headerContextMenu.menu('show', {  
        left : e.pageX,  
        top : e.pageY  
    });  
};  

$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;  
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
*/
/**
 * date扩展
 * @param format
 * @returns
 */
$.extend($.fn.datagrid.defaults.editors, {
	  datebox : {
	    init : function(container, options) {
	      var input = $('<input type="text">').appendTo(container);
	      options.editable=false;
	      input.datebox(options);
	      return input;
	    },
	    destroy : function(target) {
	      $(target).datebox('destroy');
	    },
	    getValue : function(target) {
	    	console.info($(target).datebox('getValue'));
	    	return $(target).datebox('getValue');//获得旧值
	    },
	    setValue : function(target, value) {
	      console.info(formatDatebox(value));
	      $(target).datebox('setValue', formatDatebox(value));//设置新值的日期格式
	    },
	    resize : function(target, width) {
	      $(target).datebox('resize', width);
	    }
	  }
});
/**
 * 
 */
$.extend($.fn.datagrid.defaults.editors, {
	  datetimebox : {
	    init : function(container, options) {
	      var input = $('<input type="text">').appendTo(container);
	      options.editable=false;
	      input.datetimebox(options);
	      return input;
	    },
	    destroy : function(target) {
	      $(target).datetimebox('destroy');
	    },
	    getValue : function(target) {
	    	console.info($(target).datetimebox('getValue'));
	    	return $(target).datetimebox('getValue');//获得旧值 日期格式
	    	//转换为毫秒当使用字符串传入后台需要转换成毫秒，如何用json对象传入不需要转换，否则会出错
//	    	return parseDate($(target).datetimebox('getValue')).getTime();
	    },
	    setValue : function(target, value) {
	      $(target).datetimebox('setValue', formatDatebox(value));//设置新值的日期格式
	    },
	    resize : function(target, width) {
	      $(target).datetimebox('resize', width);
	    }
	  }
});
/**
 * 添加EasyUi动态增加、移除Editor的方法
 */
$.extend($.fn.datagrid.methods,{
	/**
	 * 调用方法
	 * $("#dg").datagrid('addEditor',{field: 'dateCreate',editor: {
							type: 'datetimebox',
							options: {
								required: true
							}
						}});
	 * @param jq
	 * @param param
	 */
	addEditor:function(jq,param){
		if(param instanceof Array){
			$.each(param,function(index,item){
				var e = $(jq).datagrid('getColumnOption',item.field);
				e.editor = item.editor;
			});
		}else{
			var e = $(jq).datagrid('getColumnOption',param.field);
			e.editor = param.editor;
		}
	},
	/*
	 * 调用方法$('#dg').datagrid('removeEditor','dateCreate');  dateCreate为列名，如果为多个列，用数组方式传入
	 */
	removeEditor:function(jq,param){
		if(param instanceof Array){
			$.each(param,function(index,item){
				var e = $(jq).datagrid('getColumnOption',item);
				e.editor={};
			});
		}else{
			var e = $(jq).datagrid('getColumnOption',param);
			e.editor = {};
		}
	}
});

/**
*layout折叠后显示标题（修改↘夏悸到完善图标与body）
*使用：新建一个js文件中复制本代码，在easyui.min.js 后面引用即可 
*在layout的panle全局配置中,增加一个onCollapse处理title
*修改当默认折叠BUG(south需要默认展开，加载页面后在折叠，否则会报错)
*/

$.extend($.fn.layout.paneldefaults, {
    onCollapse : function () {
        //获取layout容器
        //var layout = $(this).parents("body.layout");
    	var layout = $(this).parents(".layout");
		if(layout.length == 0) layout = $(this).parents("div.layout");
        //获取当前region的配置属性
        var opts = $(this).panel("options");
        //获取key
        var expandKey = "expand" + opts.region.substring(0, 1).toUpperCase() + opts.region.substring(1);
        //从layout的缓存对象中取得对应的收缩对象
        var expandPanel = layout.data("layout").panels[expandKey];
        //针对横向和竖向的不同处理方式
        if (opts.region == "west" || opts.region == "east") {
		    if(opts.iconCls) icon = '<div class="'+ opts.iconCls +'" style="width:20px">&nbsp</div>';
            //竖向的文字打竖,其实就是切割文字加br
            var split = [];
            for (var i = 0; i < opts.title.length; i++) {
                split.push(opts.title.substring(i, i + 1) + '<br>');
            }
			if(opts.iconCls)
              expandPanel.panel("body").addClass("panel-title").css("text-align", "center").html('<div style="padding-left: 2px; background-position: left center;width:20px"><div class="'+ opts.iconCls +'" style="width:20px">&nbsp</div>' + split.join("") +'</div>');
			else
			  expandPanel.panel("body").addClass("panel-title").css("text-align", "center").html(split.join(""));
        } else {
		     if(opts.iconCls) expandPanel.panel("setTitle", '<div class="'+ opts.iconCls +'" style="padding-left: 20px; background-position: left center;">'+ opts.title +'</div>'); 
			 else
			 expandPanel.panel("setTitle", opts.title);
        }
    }
});
/**
 * 扩展combotree的多选功能
 */
$.extend($.fn.datagrid.defaults.editors, {
	combotree : {
		init : function(container, options) {
			var editor = $('<input/>').appendTo(container);
			//var editor = $('<input type="text"/>').appendTo(container);
			//options.multiple = true;
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		},
		getValue : function(target) {
			return $(target).combotree('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combotree('setValues',value.split(','));
		},
		resize : function(target, width) {
			$(target).combotree('resize', width);
		}
	}
});
/**
 * Easyui Tree方法扩展 - getLevel(获取节点级别)
 * 用法:
 * var node = $().tree("getSelected");
 * var lv =  $().tree("getLevel",node.target);
 */
$.extend($.fn.tree.methods, {
	getLevel:function(jq,target){
		var l = $(target).parentsUntil("ul.tree","ul");
		return l.length+1;
	}
});
/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 简单实现，如需高级功能，可以自由修改
 * 使用说明:
 *   在easyui.min.js之后导入本js
 *   代码案例:
 *		$("#dg").datagrid({....}).datagrid('tooltip'); 所有列
 *		$("#dg").datagrid({....}).datagrid('tooltip',['productid','listprice']); 指定列
 * @author ____′↘夏悸
 */
$.extend($.fn.datagrid.methods, {
	tooltip : function (jq, fields) {
		return jq.each(function () {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function () {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function () {
				var content = $(this).text();
				$(this).tooltip({
					content : content,
					trackMouse : true,
					onHide : function () {
						$(this).tooltip('destroy');
					}
				}).tooltip('show');
			});
		}
	}
});
/**
 * 扩展datagrid的一个方法keyCtr
 * 使用方法：
 * 在datagrid实例化之后调用这个方法。
 */
$.extend($.fn.datagrid.methods, {
	keyCtr : function (jq) {
		return jq.each(function () {
			var grid = $(this);
			grid.datagrid('getPanel').panel('panel').attr('tabindex', 1).bind('keydown', function (e) {
				switch (e.keyCode) {
				case 38: // up
					var selected = grid.datagrid('getSelected');
					if (selected) {
						var index = grid.datagrid('getRowIndex', selected);
						grid.datagrid('selectRow', index - 1);
					} else {
						var rows = grid.datagrid('getRows');
						grid.datagrid('selectRow', rows.length - 1);
					}
					break;
				case 40: // down
					var selected = grid.datagrid('getSelected');
					if (selected) {
						var index = grid.datagrid('getRowIndex', selected);
						grid.datagrid('selectRow', index + 1);
					} else {
						grid.datagrid('selectRow', 0);
					}
					break;
				}
			});
		});
	}
});
/**
 * 扩展tree，combotree，增加扁平化传出tree 的json串
 * 使用方法
 * 在data-options="url:'${pageContext.request.contextPath}/menuAction!getAllTreeNode.action',parentField:'pid',lines:true"
 * 中指定parentField:'pid'
 * 并且返回的json中要包含pid就可以了
 */
lh.loadFilter = {
		loadFilter : function(data, parent) {
			var opt = $(this).data().tree.options;
			var idField, textField, parentField;
			if (opt.parentField) {
				idField = opt.idField || 'id';
				textField = opt.textField || 'text';
				parentField = opt.parentField || 'pid';
				var i, l, treeData = [], tmpMap = [];
				for (i = 0, l = data.length; i < l; i++) {
					tmpMap[data[i][idField]] = data[i];
				}
				for (i = 0, l = data.length; i < l; i++) {
					if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
						if (!tmpMap[data[i][parentField]]['children'])
							tmpMap[data[i][parentField]]['children'] = [];
						data[i]['text'] = data[i][textField];
						tmpMap[data[i][parentField]]['children'].push(data[i]);
					} else {
						data[i]['text'] = data[i][textField];
						treeData.push(data[i]);
					}
				}
				return treeData;
			}
			return data;
		}
};
/*
$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	if(data){
		if (opt.parentField) {
			idFiled = opt.idFiled || 'id';
			textFiled = opt.textFiled || 'text';
			parentField = opt.parentField;
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idFiled]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][textFiled];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][textFiled];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	}
};
*/
$.extend($.fn.combotree.defaults, lh.loadFilter);
$.extend($.fn.tree.defaults, lh.loadFilter);
/**
 * tabs如果使用ifrome是当使用IE是回收内存，适用1.2.6后的easyui版本
 */
$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	/*
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		} else {
			$(this).find('.combo-f').each(function() {
				var panel = $(this).data().combo.panel;
				panel.panel('destroy');
			});
		}
	} catch (e) {
	}
	*/
	///*
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			for (var i = 0; i < frame.length; i++) {
				frame[i].src = '';
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
				try {
					CollectGarbage();
				} catch (e) {
				}
			}
		}
	} catch (e) {
	}
	//*/
};
/**
 * 防止超出浏览器边界
 * @param left
 * @param top
 * @returns
 */
var easyuiPanelOnMove = function(left, top) {/* 防止超出浏览器边界 */
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
/**
 * datagrid返回记录为0时显示“没有记录”
 * $('#dg').datagrid({
	  view: myview,
	  emptyMsg: 'no records found'
	});
 */
/*
var myview = $.extend({},$.fn.datagrid.defaults.view,{
	onAfterRender:function(target){
		$.fn.datagrid.defaults.view.onAfterRender.call(this,target);
		var opts = $(target).datagrid('options');
		var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
		vc.children('div.datagrid-empty').remove();
		if (!$(target).datagrid('getRows').length){
			var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
			d.css({
				position:'absolute',
				left:0,
				top:50,
				width:'100%',
				textAlign:'center'
			});
		}
	}
});
$.fn.datagrid.defaults.view = myview;
*/
/**
Jquery easyui datagrid js导出excel
修改自extgrid导出excel
* allows for downloading of grid data (store) directly into excel
* Method: extracts data of gridPanel store, uses columnModel to construct XML excel document,
* converts to Base64, then loads everything into a data URL link.
*
* @author Animal <extjs support team>
*
*/
$.extend($.fn.datagrid.methods, {
    getExcelXml: function (jq, param) {
        var worksheet = this.createWorksheet(jq, param);
        //alert($(jq).datagrid('getColumnFields'));
        var totalWidth = 0;
        var cfs = $(jq).datagrid('getColumnFields');
       // for (var i = 1; i < cfs.length; i++) {
        for (var i = 0; i < cfs.length; i++) {////////////////////更正这里，会导致第一列丢失，应该从0开始遍历
            totalWidth += $(jq).datagrid('getColumnOption', cfs[i]).width;
        }
        //var totalWidth = this.getColumnModel().getTotalWidth(includeHidden);
        return '<?xml version="1.0" encoding="utf-8"?>' +//xml申明有问题，以修正，注意是utf-8编码，如果是gb2312，需要修改动态页文件的写入编码
    '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">' +
    '<o:DocumentProperties><o:Title>' + param.title + '</o:Title></o:DocumentProperties>' +
    '<ss:ExcelWorkbook>' +
    '<ss:WindowHeight>' + worksheet.height + '</ss:WindowHeight>' +
    '<ss:WindowWidth>' + worksheet.width + '</ss:WindowWidth>' +
    '<ss:ProtectStructure>False</ss:ProtectStructure>' +
    '<ss:ProtectWindows>False</ss:ProtectWindows>' +
    '</ss:ExcelWorkbook>' +
    '<ss:Styles>' +
    '<ss:Style ss:ID="Default">' +
    '<ss:Alignment ss:Vertical="Top"  />' +
    '<ss:Font ss:FontName="arial" ss:Size="10" />' +
    '<ss:Borders>' +
    '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />' +
    '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />' +
    '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />' +
    '<ss:Border ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />' +
    '</ss:Borders>' +
    '<ss:Interior />' +
    '<ss:NumberFormat />' +
    '<ss:Protection />' +
    '</ss:Style>' +
    '<ss:Style ss:ID="title">' +
    '<ss:Borders />' +
    '<ss:Font />' +
    '<ss:Alignment  ss:Vertical="Center" ss:Horizontal="Center" />' +
    '<ss:NumberFormat ss:Format="@" />' +
    '</ss:Style>' +
    '<ss:Style ss:ID="headercell">' +
    '<ss:Font ss:Bold="1" ss:Size="10" />' +
    '<ss:Alignment  ss:Horizontal="Center" />' +
    '<ss:Interior ss:Pattern="Solid"  />' +
    '</ss:Style>' +
    '<ss:Style ss:ID="even">' +
    '<ss:Interior ss:Pattern="Solid"  />' +
    '</ss:Style>' +
    '<ss:Style ss:Parent="even" ss:ID="evendate">' +
    '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +
    '</ss:Style>' +
    '<ss:Style ss:Parent="even" ss:ID="evenint">' +
    '<ss:NumberFormat ss:Format="0" />' +
    '</ss:Style>' +
    '<ss:Style ss:Parent="even" ss:ID="evenfloat">' +
    '<ss:NumberFormat ss:Format="0.00" />' +
    '</ss:Style>' +
    '<ss:Style ss:ID="odd">' +
    '<ss:Interior ss:Pattern="Solid"  />' +
    '</ss:Style>' +
    '<ss:Style ss:Parent="odd" ss:ID="odddate">' +
    '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +
    '</ss:Style>' +
    '<ss:Style ss:Parent="odd" ss:ID="oddint">' +
    '<ss:NumberFormat ss:Format="0" />' +
    '</ss:Style>' +
    '<ss:Style ss:Parent="odd" ss:ID="oddfloat">' +
    '<ss:NumberFormat ss:Format="0.00" />' +
    '</ss:Style>' +
    '</ss:Styles>' +
    worksheet.xml +
    '</ss:Workbook>';
    },
    createWorksheet: function (jq, param) {
        // Calculate cell data types and extra class names which affect formatting
        var cellType = [];
        var cellTypeClass = [];
        //var cm = this.getColumnModel();
        var totalWidthInPixels = 0;
        var colXml = '';
        var headerXml = '';
        var visibleColumnCountReduction = 0;
        var cfs = $(jq).datagrid('getColumnFields');
        var colCount = cfs.length;
        //for (var i = 1; i < colCount; i++) {
        for (var i = 0; i < colCount; i++) {////////////////////更正这里，会导致第一列丢失，应该从0开始遍历
            if (cfs[i] != '') {
                var w = $(jq).datagrid('getColumnOption', cfs[i]).width;
                totalWidthInPixels += w;
                if (cfs[i] === "") {
                    cellType.push("None");
                    cellTypeClass.push("");
                    ++visibleColumnCountReduction;
                }
                else {
                    colXml += '<ss:Column ss:AutoFitWidth="1" ss:Width="130" />';
                    headerXml += '<ss:Cell ss:StyleID="headercell">' +
                '<ss:Data ss:Type="String">' + $(jq).datagrid('getColumnOption', cfs[i]).title + '</ss:Data>' +
                '<ss:NamedCell ss:Name="Print_Titles" /></ss:Cell>';
                    cellType.push("String");
                    cellTypeClass.push("");
                }
            }
        }
        var visibleColumnCount = cellType.length - visibleColumnCountReduction;
        var result = {
            height: 9000,
            width: Math.floor(totalWidthInPixels * 30) + 50
        };
        var rows = $(jq).datagrid('getRows');
        // Generate worksheet header details.
        var t = '<ss:Worksheet ss:Name="' + param.title + '">' +
    '<ss:Names>' +
    '<ss:NamedRange ss:Name="Print_Titles" ss:RefersTo="=\'' + param.title + '\'!R1:R2" />' +
    '</ss:Names>' +
    '<ss:Table x:FullRows="1" x:FullColumns="1"' +
    ' ss:ExpandedColumnCount="' + (visibleColumnCount + 2) +
    '" ss:ExpandedRowCount="' + (rows.length + 2) + '">' +
    colXml +
    '<ss:Row ss:AutoFitHeight="1">' +
    headerXml +
    '</ss:Row>';
        // Generate the data rows from the data in the Store
        //for (var i = 0, it = this.store.data.items, l = it.length; i < l; i++) {
        for (var i = 0, it = rows, l = it.length; i < l; i++) {
            t += '<ss:Row>';
            var cellClass = (i & 1) ? 'odd' : 'even';
            r = it[i];
            var k = 0;
           // for (var j = 1; j < colCount; j++) {
            for (var j = 0; j < colCount; j++) {////////////////////更正这里，会导致第一列丢失，应该从0开始遍历
                //if ((cm.getDataIndex(j) != '')
                if (cfs[j] != '') {
                    //var v = r[cm.getDataIndex(j)];
                    var v = r[cfs[j]];
                    if (cellType[k] !== "None") {
                        t += '<ss:Cell ss:StyleID="' + cellClass + cellTypeClass[k] + '"><ss:Data ss:Type="' + cellType[k] + '">';
                        if (cellType[k] == 'DateTime') {
                            t += v.format('Y-m-d');
                        } else {
                            t += v;
                        }
                        t += '</ss:Data></ss:Cell>';
                    }
                    k++;
                }
            }
            t += '</ss:Row>';
        }
        result.xml = t + '</ss:Table>' +
    '<x:WorksheetOptions>' +
    '<x:PageSetup>' +
    '<x:Layout x:CenterHorizontal="1" x:Orientation="Landscape" />' +
    '<x:Footer x:Data="Page &amp;P of &amp;N" x:Margin="0.5" />' +
    '<x:PageMargins x:Top="0.5" x:Right="0.5" x:Left="0.5" x:Bottom="0.8" />' +
    '</x:PageSetup>' +
    '<x:FitToPage />' +
    '<x:Print>' +
    '<x:PrintErrors>Blank</x:PrintErrors>' +
    '<x:FitWidth>1</x:FitWidth>' +
    '<x:FitHeight>32767</x:FitHeight>' +
    '<x:ValidPrinterInfo />' +
    '<x:VerticalResolution>600</x:VerticalResolution>' +
    '</x:Print>' +
    '<x:Selected />' +
    '<x:DoNotDisplayGridlines />' +
    '<x:ProtectObjects>False</x:ProtectObjects>' +
    '<x:ProtectScenarios>False</x:ProtectScenarios>' +
    '</x:WorksheetOptions>' +
    '</ss:Worksheet>';
        return result;
    }
});

