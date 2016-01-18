package lh.app.shop.core.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.app.shop.core.pageModel.Category;
import lh.app.shop.core.service.CategoryServiceI;
import lh.base.BaseLog;
import lh.base.action.BaseAction;
import lh.util.DataGrid;
import lh.util.Json;
import lh.util.file.FileUtil;
import lh.util.uploadServerlet.UploadUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;

@ParentPackage("public")
@Namespace("/common")
@Action(value = "categoryAction", results = { @Result(name = "editUI", type = "freemarker", location = "/jsps/shop/admin/category/category_edit.jsp"),
		@Result(name="addChildrenUI",type="freemarker",location="/jsps/shop/admin/category/category_addChildren.jsp"),
		@Result(name="addUI",type="freemarker",location="/jsps/shop/admin/category/category_add.jsp"),
		@Result(name="listUI",type="freemarker",location="/jsps/shop/admin/category/category_list.jsp")})
/*
 * @Action(value = "login",results =
 * {@Result(name="success",type="freemarker",location
 * ="/WEB-INF/html/welcome.html"),
 * 
 * @Result(name="login",type="freemarker",location="/WEB-INF/jsp/welcome.jsp")})
 */
public class CategoryAction extends BaseAction<Category> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1501387774024299302L;
	@Resource
	private CategoryServiceI categoryService;

	/**
	 * 获取商品分类方法一次全部取出 2015-12-20下午4:50:25
	 */
	public void getTreeGrid() {
		Map<String, Object> params = new HashMap<String, Object>();
		// String hql = " from Tmenu ";
		// String hqlCount = "select count(*) " + hql;
		List<Category> categorys = categoryService.getTreeGrid();
		DataGrid datagrid = new DataGrid();
		// datagrid.setTotal(categoryService.findCount(hqlCount, params));
		datagrid.setRows(categorys);
		Map<String, Object> treegridmap = new HashMap<String, Object>();
		treegridmap.put("rows", datagrid.getRows());
		this.writeJson(datagrid);
	}

	public void getAllTreeNode() {
		this.writeJson(categoryService.getAllTreeNode());
	}

	/**
	 * 修改商品分类 2015-12-25上午9:27:41
	 */
	public void edit() {
		Json json = new Json();
		if (!StringUtils.isBlank(model.getPid())
				&& !"-".endsWith(model.getPid())) {
			Category c = categoryService.getById(model.getPid());
			if ((!StringUtils.isBlank(c.getPid()) && c.getPid().equals(
					model.getId()))) {
				json.setMsg("保存商品分类失败:上级分类不能选择当前分类或其子分类");
				json.setSuccess(false);
				this.writeJson(json);
			}
		}
		if (model.getId().equals(model.getPid())) {
			json.setMsg("保存商品分类失败:上级分类不能选择当前分类或其子分类");
			json.setSuccess(false);
			this.writeJson(json);
		} else {
			if (model.getImage() != null) {
				if (FileUtil.isAllowUp(model.getImageFileName())) {
					model.setImgPath(UploadUtil.upload(model.getImage(),
							model.getImageFileName(), "goodscat"));
				} else {
					json.setMsg("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
				}
			}
			try {
				Category category = categoryService.update(model);
				json.setMsg("保存商品分类成功！");
				json.setObj(category);
				json.setSuccess(true);

			} catch (Exception e) {
				e.printStackTrace();
				json.setMsg("保存商品分类失败！" + e.getMessage());
				json.setSuccess(false);
			} finally {
				this.writeJson(json);
			}
		}
	}

	/**
	 * 添加商品分类 2015-12-25上午9:36:44
	 */
	public void add() {
		Json json = new Json();
		if (!StringUtils.isBlank(model.getPid())
				&& !"-".endsWith(model.getPid())) {
			Category c = categoryService.getById(model.getPid());
			if ((!StringUtils.isBlank(c.getPid()) && c.getPid().equals(
					model.getId()))) {
				json.setMsg("保存商品分类失败:上级分类不能选择当前分类或其子分类");
				json.setSuccess(false);
				this.writeJson(json);
			}
		}
		if (model.getImage() != null) {
			if (FileUtil.isAllowUp(model.getImageFileName())) {
				model.setImgPath(UploadUtil.upload(model.getImage(),
						model.getImageFileName(), "goodscat"));
			} else {
				json.setMsg("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
			}
		}
		try {
			Category category = categoryService.save(model);
			json.setMsg("保存商品分类成功！");
			json.setObj(category);
			json.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("保存商品分类失败！" + e.getMessage());
			json.setSuccess(false);
		} finally {
			this.writeJson(json);
		}
	}

	/**
	 * 删除商品分类 2015-12-25上午9:46:03
	 */
	public void delete() {
		Json json = new Json();
		try {
			int deleteRow = categoryService.delete(this.model.getId());
			json.setSuccess(true);
			json.setObj(model);
			json.setMsg("删除分类" + this.model.getText() + "成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除分类" + this.model.getText() + "失败！");
			json.setSuccess(false);
		} finally {
			writeJson(json);
		}
	}
	/*
	 * 保存排序
	 */
	public void savaOrder(){
		Json json = new Json();
		try {
			int rows = categoryService.saveOrder(this.model.getIds(),this.model.getCategoryOrders());
			json.setSuccess(true);
			json.setObj(model);
			json.setMsg("保存分类排序" + rows + "成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("保存分类排序" + "失败！");
			json.setSuccess(false);
		} finally {
			writeJson(json);
		}
	}
	public String editUI() {
		Category category = categoryService.getById(this.model.getId());
		BeanUtils.copyProperties(category, model);
		return "editUI";
	}
	public String addChildrenUI() {
		Category category = categoryService.getById(this.model.getId());
		BeanUtils.copyProperties(category, model);
		return "addChildrenUI";
	}
	public String addUI(){
		return "addUI";
	}
	public String listUI(){
		return "listUI";
	}
}
