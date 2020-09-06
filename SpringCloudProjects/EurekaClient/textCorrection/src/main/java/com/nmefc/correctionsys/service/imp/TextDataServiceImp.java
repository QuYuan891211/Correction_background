package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.service.TextDataService;
import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@Description:3．每日文本存储功能描述
 *@Param:
 *@Return:
 *@Author: QuYuan
 *@Date: 2020/5/6 20:15
 */
@Service("textDataService")
public class TextDataServiceImp extends BaseServiceImp<TextData,Integer,TextDataExample> implements TextDataService{
    @Autowired
    private TextDataMapper textDataMapper;
//    @Autowired
//   private TextInfoMapper textInfoMapper;
    @Autowired
    private TextInfoService textInfoService;
    @Override
    boolean checkParameters(TextData textData) {
        return false;
    }
/**
 *@Description:（6）根据模板id查询当日文本记录：根据tid再text_data表中查询是否有记录；
若记录数为0，返回空；
若记录数为1，则将其内容填至TextData返回；
若记录数大于1，则选其中t_version值最大的记录的内容填至TextData返回，将剩余的记录从数据库删除.
 *@Param: [id]
 *@Return: com.nmefc.correctionsys.entity.TextData
 *@Author: QuYuan
 *@Date: 2020/5/6 20:19
 */
    @Transactional
    public TextData getTextDataById(Integer tid){
        List<TextData> textDataList = new ArrayList<>();
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.createCriteria().andTidEqualTo(tid);
        textDataExample.setOrderByClause("t_version DESC");
        //注意这里如果要返回数据库TEXT类型，必须要用WithBLOBs方法
        textDataList = textDataMapper.selectByExampleWithBLOBs(textDataExample);
        switch (textDataList.size()){
//            若记录数为0，返回空；
            case 0:
                return null;
//            若记录数为1，则将其内容填至TextData返回；
            case 1:
                return textDataList.get(0);
//            若记录数大于1，则选其中t_version值最大的记录的内容填至TextData返回，将剩余的记录从数据库删除.
            default:
//[to-do]这个方法要加入事务控制
               Integer newVersion = textDataList.get(0).gettVersion();
               TextDataExample textDataExampleDel = new TextDataExample();
               //多条件要用.or()方法
                textDataExampleDel.or().andTidEqualTo(tid).andTVersionLessThan(newVersion);
               try{
                   textDataMapper.deleteByExample(textDataExampleDel);
               }catch (Exception ex){
                   System.out.println(ex.getMessage());
               }
               return textDataList.get(0);
        }

    }
    /**
     *@Description:（7）查询当日已编辑文本记录: 从text_data库中查询所有数据并返回
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextData>
     *@Author: QuYuan
     *@Date: 2020/5/7 9:00
     */
    public List<TextData> getAll(){
        List<TextData> textDataList = new ArrayList<>();
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.setOrderByClause("t_version DESC");
        try{
            textDataList = textDataMapper.selectByExampleWithBLOBs(textDataExample);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }finally {
            return textDataList;
        }

    }
    /**
     *@Description: （12）审核人员签名:查询当日已编辑文本记录，根据TextData.id从所查询到的列表中选出记录；
    检查所选出的记录中isok是否为1；
    是则将text_data库表中对应记录的checker填入审核人员名；
    否则提示“预报员未确认完成”
     *@Param: [id]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/7 9:51
     */
    @Transactional
    public Integer lastCheck(TextData textData){
        //[to-do]做完登录系统后，这里面要改为从Session中得到username
        textData.setChecker("默认审核员");
        textData.setGmtModified(new Date());

        try{
            return textDataMapper.updateByPrimaryKeyWithBLOBs(textData);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }
/**
 *@Description:（13）审核人员取消签名:查询当日已编辑文本记录，根据TextData.id从所查询到的列表中选出记录；
检查所选出的记录中审核人员名称与现登录人员是否相同；
相同则将text_data库表中对应记录的checker置空；
否则提示“审核人员身份验证错误”
 *@Param: [textData]
 *@Return: java.lang.Integer
 *@Author: QuYuan
 *@Date: 2020/5/7 13:03
 */
    @Transactional
    public Integer cancelLastCheck(TextData textData){
        //[to-do]做完登录系统后，这里面要改为从Session中得到username
        if("默认审核员".equals(textData.getChecker())){
            textData.setChecker(null);
            textData.setGmtModified(new Date());
            try{
                return textDataMapper.updateByPrimaryKeyWithBLOBs(textData);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        return 0;
    }
    /**
     *@Description:（9）根据文本记录查询文本模板:查询当日已编辑文本记录，根据其tid、t_version从text_info表中查询相关记录并返回
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/7 14:00
     */
    public List<TextInfo> getTextInfoByTextData(){
        List<TextData> textDataList = new ArrayList<>();
        List<TextInfo> textInfoList = new ArrayList<>();
        textDataList = getAll();
        textDataList.forEach(item ->{
            if(item.getTid() != null && item.gettVersion() != null){
                TextInfoKey textInfoKey = new TextInfoKey();
                textInfoKey.setTid(item.getTid());
                textInfoKey.settVersion(item.gettVersion());
                textInfoList.add(textInfoService.selectByPrimaryKey(textInfoKey));
            }
        });
        return textInfoList;
    }
//    /**
//     *@Description:（2）根据id删除文本记录
//     *@Param: [id]
//     *@Return: java.lang.Integer
//     *@Author: QuYuan
//     *@Date: 2020/5/7 14:38
//     */
//    public Integer deleteById(Integer id){
//        return deleteByPrimaryKey(id);
//    }
    /**
     *@Description:（1）根据模板新建文本记录: 新建一条text_data记录，tid=TextInfo.tid，t_version为最新版本号，
     * date为当天日期，creat_time为当前时间，isok=0；f
     * orecaster、checker、t_data为空
     *@Param: [textInfo]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/7 14:48
     */
    @Transactional
    public Integer saveOneTextDataByTextInfo(TextInfo textInfo){

        //1.获取最新版本号
        Integer latestVersion = textInfoService.getVersionListById(textInfo.getTid()).get(0).gettVersion();
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.or().andTidEqualTo(textInfo.getTid()).andTVersionEqualTo(latestVersion);
        List<TextData> textDataList = new ArrayList<>();
        textDataList = selectByExample(textDataExample);
        if(textDataList.size() < 1){
            //没有则新建
          TextData textData = new TextData();
          textData.setGmtModified(new Date());
          textData.setDate(new Date());
          textData.setIsok(false);
          textData.setGmtCreate(new Date());
          textData.setTid(textInfo.getTid());
          textData.settVersion(latestVersion);
          //因为text类型的字段未赋值，所以可以使用不带BLOBS的方法
          return insertSelective(textData);
        }else {
            return 0;
        }
    }
    /**
     *@Description: （5）更新当日文本记录:根据id在text_data表中查询记录，
    若查到记录，则更新t_data、creat_time、isok置0；
    若未查到记录，则报错
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/8 11:34
     */
    @Transactional
    public Integer update(TextData textData){
        textData.setIsok(false);
        textData.setGmtModified(new Date());
        try{
           TextData textDataOld = selectByPrimaryKey(textData.getId());
           if (textDataOld == null){return 0;}
           textData.setGmtCreate(textDataOld.getGmtCreate());
           textData.setDate(textDataOld.getDate());
           textData.setChecker(null);
           textData.setForecaster(textDataOld.getForecaster());
           textData.setTid(textDataOld.getTid());
           textData.settVersion(textData.gettVersion());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try{
            return textDataMapper.updateByPrimaryKeyWithBLOBs(textData);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }
    /**
     *@Description:（10）预报员确认完成: 查询text_data库表中对应记录，
     * 将对应记录中的forecaster填入预报员名，
     * 将isok置1，creat_time更新为当前时间；
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 9:25
     */
    @Transactional
    public Integer checkByForecaster(TextData textData){
        TextData data = selectByPrimaryKey(textData.getId());
//        [to-do]后续要从登录系统中获取
        if(data == null){return 0;}
        data.setForecaster("默认预报员");
        data.setGmtModified(new Date());
        data.setIsok(true);
        return textDataMapper.updateByPrimaryKeyWithBLOBs(data);
    }
    /**
     *@Description: （11）预报员取消确认:查询text_data库表中对应记录；
    检查TextData.checker是否为空：
    若为空则将对应记录中的forecaster填入预报员名，将isok置0，creat_time更新为当前时间；
    否则报错“应先取消审核”
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 9:53
     */
    @Transactional
    public Integer uncheckByForecaster(TextData textData){
        TextData data = selectByPrimaryKey(textData.getId());
        if(data == null || data.getChecker() != null){return 0;}
        data.setForecaster("默认预报员");
        data.setGmtModified(new Date());
        data.setIsok(false);
        System.out.println(data.getIsok());
        return textDataMapper.updateByPrimaryKeyWithBLOBs(data);
    }
    /**
     *@Description: 删除全部
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 16:14
     */
    @Transactional
    public Integer deleteAll(){
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.createCriteria().andIdIsNotNull();
//        int count = 0;
        try{
            return textDataMapper.deleteByExample(textDataExample);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
//        [to-do]使用枚举约束
        return 9999;
    }
}
