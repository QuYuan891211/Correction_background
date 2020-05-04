package com.nmefc.correctionsys.service;

import java.util.List;

public interface BaseService<T,K,E> {
    List<T> selectByExample(E e);
    T selectByPrimaryKey(K k);
}
