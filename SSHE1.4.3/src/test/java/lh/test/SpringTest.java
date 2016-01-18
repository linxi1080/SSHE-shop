package lh.test;

import lh.service.UserServiceI;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] {"classpath:spring.xml"});
		UserServiceI userService = (UserServiceI) ac.getBean("userService");
		userService.test();
	}
}
