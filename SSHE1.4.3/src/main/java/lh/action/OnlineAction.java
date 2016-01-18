package lh.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.action.BaseAction;
import lh.entity.Tonline;
import lh.service.OnlineServiceI;
import lh.util.DataGrid;
import lh.util.Json;
import lh.util.excel.ExcelUtil;
import lh.util.excel.TestPoi1;
import lh.util.ireport.Reports;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

@ParentPackage("basePackage")
// @ParentPackage("noBasePackage")//需要在struts.xml中定义对应的package
@Namespace("/")
// @Namespace("/lh")//可以重新定义命名空间
@Action(value = "onlineAction")
public class OnlineAction extends BaseAction<Tonline> {
	private static final long serialVersionUID = -8298509130914840499L;
	private static final Logger logger = Logger.getLogger(OnlineAction.class);

	@Resource
	private OnlineServiceI onlineService;
	private Date datefrom;
	private Date dateto;
	private String ids;

	public Date getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(Date datefrom) {
		this.datefrom = datefrom;
	}

	public Date getDateto() {
		return dateto;
	}

	public void setDateto(Date dateto) {
		this.dateto = dateto;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void find() {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tonline t ";

		String hqlWhere = addWhere(params);

		hql += hqlWhere;
		String hqlCount = "select count(*) " + hql;

		List<Tonline> users = new ArrayList<Tonline>();
		users = onlineService.find(hql, params, this.page, this.rows,
				this.sort, this.order);
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(onlineService.findCount(hqlCount, params));
		dataGrid.setRows(users);
		writeJson(dataGrid);
	}
	public void doNotNeedGrant_find() {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tonline t ";

		String hqlWhere = addWhere(params);

		hql += hqlWhere;
		String hqlCount = "select count(*) " + hql;

		List<Tonline> users = new ArrayList<Tonline>();
		users = onlineService.find(hql, params, this.page, this.rows,
				this.sort, this.order);
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(onlineService.findCount(hqlCount, params));
		dataGrid.setRows(users);
		writeJson(dataGrid);
	}
	private String addWhere(Map<String, Object> params) {
		String hqlWhere = " where 1=1 ";
		if (this.getDatefrom() != null && this.getDateto() != null) {
			hqlWhere += " and t.createdatetime >= :date_from and t.createdatetime <= :date_to ";
			params.put("date_from", this.getDatefrom());
			params.put("date_to", this.getDateto());
		}
		if (this.model.getLoginname() != null
				&& !this.model.getLoginname().trim().equals("")) {
			hqlWhere += " and t.loginname like :loginname";
			params.put("loginname", "%" + this.model.getLoginname() + "%");
		}
		if (this.model.getIp() == null || this.model.getIp().trim().equals("")) {

		} else {
			hqlWhere += " and t.ip like :ip ";
			params.put("ip", "%" + this.model.getIp().trim() + "%");
		}
		if (this.model.getType() != null
				&& !this.model.getType().trim().equals("")) {
			hqlWhere += " and t.type like :type ";
			params.put("type", "%" + this.model.getType() + "%");
		}
		return hqlWhere;
	}

	public void delete() {
		Json json = new Json();
		try {
			// int i = onlineService.executeHql(this.getIds());
			int i = onlineService.delete(this.getIds());
			json.setSuccess(true);
			json.setMsg(i + "个用户登录记录删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除失败！" + e.getMessage());
		} finally {
			writeJson(json);
		}
	}

	public void doNotNeedGrant_onlineCreateDatetimeChart() {
		writeJson(onlineService.userCreateDatetimeChart());
	}

	// /////////////////////////
	public void writeExcel() {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tonline t ";
		List<Tonline> users = new ArrayList<Tonline>();

		users = onlineService.find(hql, params, this.page, this.rows,
				this.sort, this.order);
		// String[] head = {"用户登录日志"};
		// List<Tonline[]> objects = {users};
		// ExcelTools.writeExcel(this.response,head,"",users);
	}
	public void doNotNeedGrant_report(){
		//定义参数map
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("title","我的报表"); //添加参数
		//定义报表文件路径
		String url = "jasper/report2.jasper";//report2.jasper
		//F:/apache-tomcat-7.0.54/webapps/SSHE/jasper/report2.jasper
		Reports report = new Reports();//创建报表类对象
		//调用生成表方法
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tonline t ";
		List<Tonline> list = new ArrayList<Tonline>();

		list = onlineService.find(hql, params, this.page, this.rows,
				this.sort, this.order);
		//report.selectHtmlReport(list, response, url, parameters);//调用生成表方法
		report.selectHtmlReport(list, response, url, parameters);

//		report.selectExcelReport(list,response,url,parameters,"asdfa");
	}
	public void doNotNeedGrant_excelTest() {
		// try {
		// TestPoi1.testWriteExcel();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		try {

			ExcelUtil eu = new ExcelUtil();
			eu.setExcelPath("d:\\2.xlsx");

			System.out.println("=======测试Excel 默认 读取========");
			eu.readExcel();

			System.out.println("\n=======测试Excel 从第四行读取，倒数第二行结束========");
			eu = eu.RestoreSettings();// 还原设定
			eu.setStartReadPos(3);
			eu.setEndReadPos(-1);
			eu.readExcel();

			System.out.println("\n=======测试Excel 读取第二个sheet========");
			eu = eu.RestoreSettings();// 还原设定
			eu.setSelectedSheetIdx(1);
			eu.readExcel();

			System.out.println("\n=======测试Excel 读取所有的sheet========");
			eu = eu.RestoreSettings();// 还原设定
			eu.setOnlyReadOneSheet(false);
			eu.readExcel();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
