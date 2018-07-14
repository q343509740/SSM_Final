package com.ray.test;

import com.github.pagehelper.PageHelper;
import com.ray.dao.CityMapper;
import com.ray.entity.City;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Ray
 * @date 2018/7/14 0014
 */
public class CityTest extends BaseTest {

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void selectByPage(){

        // 分页查询,从第page页开始，每页显示rows条记录
        PageHelper.startPage(2,5);
        // Mapper提供的查询所有信息方法
        List<City> cities = cityMapper.selectAll();
    }
}
