package com.nmefc.correctionsys.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: Dao的基础类,其中T为实体，K为主键实体,E为Example
 *@Param:
 *@Return:
 *@Author: QuYuan
 *@Date: 2020/5/4 15:03
 */
public interface BaseMapper<T,K,E> {
    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(K key);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(K key);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
