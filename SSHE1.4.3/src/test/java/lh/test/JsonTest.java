package lh.test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lh.entity.Tuser;
import lh.util.DataGrid;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class JsonTest {
	protected PrintWriter out;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		List<Tuser> list = new ArrayList<Tuser>();
//		out.print(object);
		DataGrid dg = new DataGrid();
		dg.setTotal(0L);
		dg.setRows(list);
		String json = JSON.toJSONStringWithDateFormat(dg, "yyyy-MM-dd HH:mm:ss");
		System.out.println(json);
//		out.print(json);
//		out.flush();
//		out.close();
	}

}
