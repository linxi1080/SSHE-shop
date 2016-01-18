<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		indexWestMenuTree = $('#jsp_layout_west_menuTree');
		indexWestMenuTree
				.tree({
					lines : true,
					//url : '${pageContext.request.contextPath}/menuAction!doNotNeedGrant_getAllTreeNode.action',
					//getTreeNodeByUser
					url : '${pageContext.request.contextPath}/menuAction!doNotNeedGrant_getTreeNodeByUser.action',
					/**
					 * 双击展开树
					 * @param {Object} node
					 */
					parentField : 'pid',
					onDblClick : function(node) {
						if (node.state == 'closed') {
							indexWestMenuTree.tree('expand', node.target);
						} else {
							indexWestMenuTree.tree('collapse', node.target);
						}
					},
					/**
					 * 单击
					 */
					onClick : function(node) {
						var _title = node.text;
						var _icon = node.iconCls;
						if (node.attributes.url) {
							var _url = '${pageContext.request.contextPath}'
									+ node.attributes.url;
							if (node.attributes.target && node.attributes.target.length > 0) {
								window.open(src, node.attributes.target);
							}else{
								if(node.attributes.openMode){
									addTabiFrame('jsp_layout_center_tabs','jsp_layout_center_tabsRigthMenu',{"title":_title,"href":_url,"iconCls":_icon});
								}else{
									addTab('jsp_layout_center_tabs', 'jsp_layout_center_tabsRigthMenu', {
										"title" : _title,
										"href" : _url,
										"iconCls" : _icon
									});
								}
							}
							
						}
					},
					/**
					 * 加载成功后折叠tree
					 */
					onLoadSuccess : function(node, data) {
						$(this).tree('collapseAll');
					}
				});
	});
</script>
<div class="easyui-panel"
	data-options="title:'功能导航',border:false,fit:true,iconCls:'icon-search',
		tools: [{iconCls:'icon-reload', 
				        handler:function(){
				            var indexWestMenuTree = $('#jsp_layout_west_menuTree');
							var node = indexWestMenuTree.tree('getSelected');							
							if(node){
								indexWestMenuTree.tree('expandAll',node.target);
							}else{
								indexWestMenuTree.tree('expandAll');
							}
						}    
				    },{    
				        iconCls:'icon-undo',    
				        handler:function(){
				        	var indexWestMenuTree = $('#jsp_layout_west_menuTree');
							var node = indexWestMenuTree.tree('getSelected');
							if(node){
								indexWestMenuTree.tree('collapseAll',node.target);
							}else{
								indexWestMenuTree.tree('collapseAll');
							}
						}    
				    }] ">
	<div class="easyui-accordion" data-options="border:false,fit:true">
		<div title="系统菜单" data-options="iconCls:'ext-icon-sitemap_color'">
			<ul id="jsp_layout_west_menuTree" class="easyui-tree"></ul>
		</div>
		<div title="Title2" data-options="iconCls:'icon-reload'"></div>
	</div>
</div>