package com.nmefc.correctionsys.service.imp;

//import com.nmefc.correctionsys.dao.BaseDao;
import com.nmefc.correctionsys.dao.BaseMapper;
import com.nmefc.correctionsys.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 *@Description: 服务层基础实现类
 *@Param:
 *@Return:
 *@Author: QuYuan
 *@Date: 2020/5/4 15:11
 */
public abstract class BaseServiceImp<T,K,E> implements BaseService<T,K,E>{
    @Autowired
    public BaseMapper<T,K,E> baseMapper;

    /**
     *@Description: 每一个服务都要进行自己的参数校验
     *@Param: []
     *@Return: boolean
     *@Author: QuYuan
     *@Date: 2020/5/4 15:11
     */
    abstract boolean checkParameters(T t);

    /**
     *@Description: 根据条件查询
     *@Param: [e]
     *@Return: java.util.List<T>
     *@Author: QuYuan
     *@Date: 2020/5/4 21:43
     */
    @Override
    public List<T> selectByExample(E e) {
        List<T> list = new ArrayList<>();
        try{
            list =  baseMapper.selectByExample(e);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            return list;
        }
    }
}