$.extend($.fn.datagrid.defaults.editors, {
	datebox : {
		init : function(container, options) {
			var input = $('<input type="text">').appendTo(container);
			input.datebox(options);
			return input;
		},
		destroy : function(target) {
			$(target).datebox('destroy');
		},
		getValue : function(target) {
			return $(target).datebox('getValue');// 获得旧值
		},
		setValue : function(target, value) {
			console.info(formatDatebox(value));
			$(target).datebox('setValue', formatDatebox(value));// 设置新值的日期格式
		},
		resize : function(target, width) {
			$(target).datebox('resize', width);
		}
	}
});