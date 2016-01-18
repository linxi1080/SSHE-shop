package lh.test;

import java.util.Date;
import java.util.UUID;

import lh.entity.Tuser;
import lh.service.UserServiceI;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHibernate {
	@Test
	public void test(){
//		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] {"classpath:spring.xml","classpath:spring-hibernate.xml"});
//		UserServiceI userService = (UserServiceI) ac.getBean("userService");
//		Tuser t = new Tuser();
//		t.setName("李四");
//		t.setPwd("123");
////		t.setId(UUID.randomUUID().toString());
//		t.setCreatedatetime(new Date());
//		t.setModifydatetime(new Date());
//		userService.save(t);
	}
	
}
