package com.ray.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Ray
 * @date 2018/7/14 0014
 */
@RunWith(SpringJUnit4ClassRunner.class)   // 使用Springtest测试框架
@ContextConfiguration({ "classpath:spring/spring-*.xml" }) // 告诉junit spring配置文件
public class BaseTest {

}
