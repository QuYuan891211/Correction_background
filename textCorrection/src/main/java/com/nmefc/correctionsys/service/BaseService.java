package com.nmefc.correctionsys.service;

import java.util.List;

public interface BaseService<T,K,E> {
    List<T> selectByExample(E e);
    T selectByPrimaryKey(K k);
    int insertSelective(T t);
    int deleteByPrimaryKey(K key);
    int insert(T t);

}
