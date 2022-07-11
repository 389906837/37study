package spring;


import com.cloud.cang.message.sms.impl.MengWangSmsSender;
import com.cloud.cang.message.sms.impl.TwoFactorSmsSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:spring/applicationContext.xml",
        "classpath*:spring/spring-mvc.xml"})
@ActiveProfiles("dev")
public class BasicSpringJunitTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	MengWangSmsSender mengWangSmsSender;
	@Autowired
	TwoFactorSmsSender twoFactorSmsSender;
	
	@Test
	public void sTest(){
		mengWangSmsSender.send("15121061739", "hello");
	}

	@Test
	public void twoTest(){
		System.out.println(twoFactorSmsSender.send("9790802191", "hello"));
	}



}
