package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
     *@Description:(6)查询指定文字模板的最新版本号:在数据库text_info中查询所有数据，
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
     *@Description:（5）查询指定文字模板 : 在数据库text_info中查询所有数据，选出指定tid和版本的任务
     *@Param: [id, version]
     *@Return: com.nmefc.correctionsys.entity.TextInfo
     *@Author: QuYuan
     *@Date: 2020/5/5 0:42
     */
    @Override
    public TextInfo getTextInfoByIdAndVersion(Integer id, Integer version) {
        TextInfoKey key = new TextInfoKey();
        key.setTid(id);
        key.settVersion(version);
        TextInfo textInfo = new TextInfo();
        try{
            textInfo = this.selectByPrimaryKey(key);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return textInfo;
    }

    /**
 *@Description:(7)查询指定文字模板的是否已删除: 在数据库text_info中查询所有数据，选出指定tid的任务，并查看其是否所有isdel==1；是则反馈true；
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
        if(textInfoList.stream().allMatch(textInfo -> textInfo.getIsDelete() == true)){return true;}
        //2.检查该isdel==0的信息是否为最新版本，若是则反馈false
//        textInfoList.stream().sorted((p1,p2) -> p2.gettVersion() - p1.gettVersion()).collect(Collectors.toList());
        //打印检查是否排序

        if(textInfoList.get(0).getIsDelete()==false) {
            return false;
        }else{
            //3.否则后台记录错误并处理（非最新版记录未软删除异常）后，反馈false
            TextInfo textInfo = new TextInfo();
            textInfo.setTid(id);
            softDeleteByTid(textInfo);
            //[to-do]Log

            return false;
        }

    }
    /**
     *@Description:（4）查询最新文字模板列表: 在数据库text_info中查询所有数据，选出未删除，且最新版本的文字模板；
     * 如department不为null则进一步筛选出复合部门要求的数据.
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/5 1:00
     */
    @Override
    public List<TextInfo> getLatestTextInfoByDepartment(Integer departmentId) {
        TextInfoExample textInfoExample = new TextInfoExample();
        List<TextInfo> textInfoList = new ArrayList<>();
        //1.找出未删除且版本最新的
        textInfoExample.setOrderByClause("tid DESC, t_version DESC");
        textInfoExample.createCriteria().andIsDeleteEqualTo(false);
        //2.如department不为null则进一步筛选出复合部门要求的数据.
        if (departmentId !=null){
            textInfoExample.createCriteria().andDepartmentEqualTo(departmentId);
        }
        textInfoList = this.selectByExample(textInfoExample);
        List<TextInfo> uniqueList = textInfoList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(TextInfo::getTid))),ArrayList::new));
        return uniqueList;
    }
    /**
     *@Description:（1）新增文字模板: 在text_info表新建一条数据，填入文字模板名（t_name）、文字模板简称（t_abbreviation）、所属部门（关联department），
     * tid自动新增，t_version=1，creat_time为当前时间，isdel=0；
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/5 11:18
     */
    @Transactional
    @Override
    public Integer saveTextInfo(TextInfo textInfo) {
        textInfo.setGmtCreate(new Date());
        textInfo.setGmtModified(new Date());
        //1.设置版本号为1
        textInfo.settVersion(1);
        return this.insertSelective(textInfo);
    }
    /**
     *@Description: （2）软删除文字模板 在数据库text_info中按tid寻找
     * 所有条目（应包括该文字模板的所有版本），并将所有条目的isdel改为1.
     *@Param: [textInfo]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/5 12:06
     */
    @Transactional
    @Override
    public Integer softDeleteByTid(TextInfo textInfo) {

        try{
            textInfo.setGmtModified(new Date());
           return textInfoMapper.softDeleteByTid(textInfo);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }
    /**
     *@Description:（8）修改文字模板（增量）:在数据库text_info中按tid寻找所有条目
     * （应包括该文字模板的所有版本），并将所有条目的isdel改为1；新增同tid号的一条数据，
     * t_version比原有最大值+1；分别检查文字模板名、文字模板简称、部门是否为空（全空则报错），
     * 不为空则将其内容复制到新的数据中，
     * 否则将上一版的相应数据复制到新的数据中.即增加新版数据，按需更新，旧版isdel置1
     *@Param: [textInfo]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/5 12:38
     */
    @Transactional
    @Override
    public Integer updateTextInfo(TextInfo textInfo) {

        //1.在数据库text_info中按tid寻找所有条目（应包括该文字模板的所有版本），并将所有条目的isdel改为1；
        Integer result = this.softDeleteByTid(textInfo);
        if (result == null || result == 0){return  0;}
        Integer newVersion = 0;
        //2.获取最新版本号
        List<TextInfo> textInfoList = new ArrayList<>();
        textInfoList = this.getVersionListById(textInfo.getTid());
        if(textInfoList.size() < 1){return  0;}
        newVersion = textInfoList.get(0).gettVersion() + 1;
        textInfo.settVersion(newVersion);
        //3.文字模板名、文字模板简称、部门是否为空（全空则报错），将上一版的相应数据复制到新的数据中.即增加新版数据，按需更新，旧版isdel置1
        if(textInfo.gettName() == null && textInfo.getDepartment() == null && textInfo.gettAbbreviation() == null){
            textInfo.settName(textInfoList.get(0).gettName() );
            textInfo.setDepartment(textInfoList.get(0).getDepartment());
            textInfo.settAbbreviation(textInfoList.get(0).gettAbbreviation());
        }

        textInfo.setGmtCreate(new Date());
        textInfo.setGmtModified(new Date());
        return this.insertSelective(textInfo);
    }
}
