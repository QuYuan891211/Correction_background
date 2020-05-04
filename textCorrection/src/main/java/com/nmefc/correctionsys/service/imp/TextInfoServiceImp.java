package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.dao.BaseMapper;
import com.nmefc.correctionsys.dao.TextInfoMapper;
import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.entity.TextInfoExample;
import com.nmefc.correctionsys.entity.TextInfoKey;
import com.nmefc.correctionsys.service.TextInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *@Description: 文本模板服务实现类
 *@Param:
 *@Return:
 *@Author: QuYuan
 *@Date: 2020/5/4 21:46
 */
@Service("textInfoService")
public class TextInfoServiceImp extends BaseServiceImp<TextInfo,TextInfoKey,TextInfoExample> implements TextInfoService {
    @Autowired
    protected TextInfoMapper textInfoMapper;

    @Override
    boolean checkParameters(TextInfo textInfo) {
        return false;
    }
    /**
     *@Description:查询指定文字模板的最新版本号:在数据库text_info中查询所有数据，
     * 选出指定tid的任务，并抽取其最新的版本号进行输出
     *@Param: [id]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/4 23:44
     */
    @Override
    public List<TextInfo> getVersionListById(Integer id){
        TextInfoExample textInfoExample = new TextInfoExample();
        //1.进行条件查询,找到此id的所有记录（联合主键）
        textInfoExample.createCriteria().andTidEqualTo(id);
        textInfoExample.setOrderByClause("t_version DESC");
        List<TextInfo> textInfoList = new ArrayList<>();
        return this.selectByExample(textInfoExample);
    }

/**
 *@Description:(8)查询指定文字模板的是否已删除: 在数据库text_info中查询所有数据，选出指定tid的任务，并查看其是否所有isdel==1；是则反馈true；
否则检查该isdel==0的信息是否为最新版本，若是则反馈false
否则后台记录错误并处理（非最新版记录未软删除异常）后，反馈false
 *@Param: [textInfoExample]
 *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
 *@Author: QuYuan
 *@Date: 2020/5/4 21:47
 */

    public boolean isDelete(Integer id) {
        List<TextInfo> textInfoList = getVersionListById(id);
        //1.如果为空，说明该id没有模板；如果不为空，但isdel==1，说明所有模板都已删，此时返回true
        if(textInfoList.size() < 1){return true;}
        if(textInfoList.stream().allMatch(textInfo -> textInfo.getIsDelete() == 1)){return true;}
        //2.检查该isdel==0的信息是否为最新版本，若是则反馈false
//        textInfoList.stream().sorted((p1,p2) -> p2.gettVersion() - p1.gettVersion()).collect(Collectors.toList());
        //打印检查是否排序

        if(textInfoList.get(0).getIsDelete()==0){return false;}
        //3.否则后台记录错误并处理（非最新版记录未软删除异常）后，反馈false
        return false;
    }


}
