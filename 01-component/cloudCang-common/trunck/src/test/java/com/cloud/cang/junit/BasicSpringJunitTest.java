package com.cloud.cang.junit;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml",
        "classpath*:spring/applicationContext-datasource.xml"  })
public class BasicSpringJunitTest extends AbstractJUnit4SpringContextTests {

}
