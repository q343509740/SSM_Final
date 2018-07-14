package com.ray.entity;

import javax.persistence.*;

@Table(name = "city")
public class City {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 城市
     */
    private String name;

    /**
     * 省份
     */
    private String state;

    /**
     * 获取编号
     *
     * @return id - 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取城市
     *
     * @return name - 城市
     */
    public String getName() {
        return name;
    }

    /**
     * 设置城市
     *
     * @param name 城市
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取省份
     *
     * @return state - 省份
     */
    public String getState() {
        return state;
    }

    /**
     * 设置省份
     *
     * @param state 省份
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}