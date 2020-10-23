package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.common.entity.Result;
import com.nmefc.correctionsys.common.enums.ResultCodeEnum;
import com.nmefc.correctionsys.common.enums.ResultMsgEnum;
import com.nmefc.correctionsys.common.utils.DateTimeUtils;
import com.nmefc.correctionsys.dao.TextDataMapper;
import com.nmefc.correctionsys.entity.*;
import com.nmefc.correctionsys.entity.API.CorrectData;
import com.nmefc.correctionsys.entity.API.CorrectPacket;
import com.nmefc.correctionsys.entity.midModel.TextDataAndTextDetailSaveModel;
import com.nmefc.correctionsys.service.TextDataService;
import com.nmefc.correctionsys.service.TextDetailService;
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
    @Autowired
    private TextInfoService textInfoService;
    @Autowired
    private TextDetailService textDetailService;
    @Override
    boolean checkParameters(TextData textData) {
        return false;
    }
/**
 *@Description:（6）根据模板id查询当日文本记录：根据tid再text_data表中查询是否有记录；
若记录数为0，返回空；
若记录数为1，则将其内容填至TextData返回；
若记录数大于1，则选其中t_version值最大的记录的内容填至TextData返回（若同一个版本有多条记录，返回最新的记录,删掉同版本当天其余的），将剩余的记录从数据库删除.
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
        textDataExample.setOrderByClause("t_version DESC, gmt_create DESC");
        //注意这里如果要返回数据库TEXT类型，必须要用WithBLOBs方法
        textDataList = textDataMapper.selectByExample(textDataExample);
        switch (textDataList.size()){
//            若记录数为0，返回空；
            case 0:
                return null;
//            若记录数为1，则将其内容填至TextData返回；
            case 1:
                return textDataList.get(0);
//            若记录数大于1，则选其中t_version值最大的记录的内容填至TextData返回，将剩余的记录(包括其它版本以及同版本同一天的)从数据库删除.
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
               //此时还会存在同TID同Version的数据，只读取最新的,删掉当天其它的。
                TextDataExample textDataExampleNext = new TextDataExample();
                textDataExampleNext.createCriteria().andTidEqualTo(tid);
                textDataExampleNext.setOrderByClause("gmt_modified DESC");
                textDataList = textDataMapper.selectByExample(textDataExampleNext);
                if(textDataList == null){return null;}
                if(textDataList.size() > 1){
                    Integer id = textDataList.get(0).getId();
                    TextDataExample textDataExampleToday = new TextDataExample();
                    textDataExampleToday.or().andTidEqualTo(tid).andTVersionEqualTo(newVersion).andGmtModifiedGreaterThan(DateTimeUtils.initDateByDay()).andIdNotEqualTo(id);
                    textDataMapper.deleteByExample(textDataExampleToday);
                }
               return textDataList.get(0);
        }

    }
    /**
     *@Description:（7）查询当日已编辑文本记录: 从text_data库中查询gmt_modified为当日的数据并返回
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextData>
     *@Author: QuYuan
     *@Date: 2020/5/7 9:00
     */
    public List<TextData> getAll(){
        List<TextData> textDataList = new ArrayList<>();
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.createCriteria().andGmtModifiedGreaterThan(DateTimeUtils.initDateByDay());
        textDataExample.setOrderByClause("tid DESC");
        try{
            textDataList = textDataMapper.selectByExample(textDataExample);
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
    public Result<TextData> lastCheck(Integer id){
        Result<TextData> result = new Result<>();
        TextData textData = textDataMapper.selectByPrimaryKey(id);
        if(null == textData){
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.NULL_DATA.getMsg());
            return result;
        }
        if(textData.getForecaster() == null){
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.NULL_FORECASTER.getMsg());
            return result;
        }
        if(!DateTimeUtils.isToday(textData.getGmtModified())){
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.NOT_TADAY.getMsg());
            return result;
        }
        //[to-do]做完登录系统后，这里面要改为从Session中得到username
        textData.setChecker("默认审核员");
        try{
            textDataMapper.updateByPrimaryKey(textData);
            result.setCode(ResultCodeEnum.SUCCESS.getCode());
            result.setMessage(ResultMsgEnum.SUCCESS.getMsg());
            return result;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.CONTACT_ADMIN.getMsg());
            return result;
        }

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
    public Result<TextData> cancelLastCheck(Integer id){
        Result<TextData> result = new Result<>();
        TextData textData = textDataMapper.selectByPrimaryKey(id);
        if(null == textData){
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.NULL_DATA.getMsg());
            return result;
        }
        if(textData.getForecaster() == null){
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.NULL_FORECASTER.getMsg());
            return result;
        }
        if(!DateTimeUtils.isToday(textData.getGmtModified())){
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.NOT_TADAY.getMsg());
            return result;
        }
        //[to-do]做完登录系统后，这里面要改为从Session中得到username
        if("默认审核员".equals(textData.getChecker())){
            textData.setChecker(null);
            try{
                textDataMapper.updateByPrimaryKey(textData);
                result.setCode(ResultCodeEnum.SUCCESS.getCode());
                result.setMessage(ResultMsgEnum.SUCCESS.getMsg());
                return result;
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                result.setCode(ResultCodeEnum.FAIL.getCode());
                result.setMessage(ResultMsgEnum.CONTACT_ADMIN.getMsg());
                return result;
            }
        }else {
            result.setCode(ResultCodeEnum.FAIL.getCode());
            result.setMessage(ResultMsgEnum.ERROR_CHECKER.getMsg());
            return result;
        }
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
    public Integer update(TextData textData, List<TextDetail> textDetailList){
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
           //处理预报文本

        }catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        try{
             textDetailList.forEach(item->{
                    item.setGmtModified(new Date());
                    textDetailService.updateTextDetail(item);
             }
             );
             textDataMapper.updateByPrimaryKey(textData);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return 0;
        }
        return 1;
    }
    /**
     *@Description:（10）预报员确认完成: 根据ID查询TEXTData表，如果查到的内容的gmt_modified和当前日期为同一天，则更新：将对应记录中的forecaster更新为新预报员名，将isok置1，gmt_modified更新为当前时间；
    如果查到的内容的gmt_modified和当前日期不是同一天，则新插入一条记录：将对应记录中的forecaster更新为新预报员名，将isok置1，gmt_time更新为当前时间；
     * 将isok置1，creat_time更新为当前时间；
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 9:25
     */
    @Transactional
    public Integer checkByForecaster(TextDataAndTextDetailSaveModel model){
        TextData data = selectByPrimaryKey(model.getId());
//        //后续要从登录系统中获取
        if (null == data){return 0;}
        data.setForecaster("默认预报员");
//        data.setIsok(true);

        data.setIsok(true);
        System.out.println(data.getGmtModified());
        if(!DateTimeUtils.isToday(data.getGmtModified())){
            //如果是当天没有textdata时
            data.setGmtModified(new Date());
            data.setId(null);
            textDataMapper.insertSelective(data);
            TextDataExample textDataExample = new TextDataExample();
            textDataExample.or().andTVersionEqualTo(data.gettVersion()).andTidEqualTo(data.getTid()).andGmtModifiedGreaterThan(DateTimeUtils.initDateByDay());
            textDataExample.setOrderByClause("gmt_modified DESC");
            Integer textDataId = textDataMapper.selectByExample(textDataExample).get(0).getId();
            //插入新的TextDetail，并关联textDataId
            List<TextDetail> textDetailList = new ArrayList<>();
            for(int i = 0; i< model.getTextDetailList().size();i++){
                TextDetail textDetail = new TextDetail();
                textDetail.setGmtCreate(new Date());
                textDetail.setGmtModified(new Date());
                textDetail.setTextDataId(textDataId);
                textDetail.setText(model.getTextDetailList().get(i));
                textDetail.setIntervalId(i+1);
                textDetailService.insertSelective(textDetail);

            }
            return 1;
        }else {
            //如果是当天有textData时
            data.setGmtModified(new Date());
            try {

                textDataMapper.updateByPrimaryKeySelective(data);
                List<TextDetail> list = textDetailService.findByTextDataId(model.getId());
                if(null == list || list.size() <1 || list.size() != model.getTextDetailList().size()){
                    return 0;
                }
                for(int i = 0; i< list.size();i++){
                    TextDetail textDetail = list.get(i);
                    textDetail.setGmtModified(new Date());
                    textDetail.setText(model.getTextDetailList().get(i));
                    textDetailService.updateTextDetail(textDetail);
                }
                return 1;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return 0;
        }
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
        return textDataMapper.updateByPrimaryKey(data);
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

    @Override
    public TextData getLastDayTextData(TextInfoKey textInfoKey) {
        if(null != textInfoKey && textInfoKey.getTid() != null && textInfoKey.gettVersion() != null){
            try{
                TextDataExample textDataExample = new TextDataExample();
                textDataExample.or().andGmtModifiedBetween(DateTimeUtils.initLastDateByDay(),DateTimeUtils.initDateByDay()).andTidEqualTo(textInfoKey.getTid()).andTVersionEqualTo(textInfoKey.gettVersion());
                textDataExample.setOrderByClause("gmt_modified DESC");
                return textDataMapper.selectByExample(textDataExample).get(0);

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else {
            return null;
        }
        return null;
    }

    @Override
    public CorrectPacket getCorrectPacketToday() {
        CorrectPacket correctPacket = new CorrectPacket();
        List<CorrectData> correctDataList = new ArrayList<>();
        List<TextData> textDataList = this.getAll();
        if(null == textDataList ){return null;}

        textDataList.forEach(item->{
            //获取当前的TextData对应的TextInfo
            TextInfo textInfo = textInfoService.getTextInfoByIdAndVersion(item.getTid(),item.gettVersion());
            //获得简称
            String abb = textInfo.gettAbbreviation();
            //获得当前textData所对应的TextDetail
            List<TextDetail> textDetailList = textDetailService.findByTextDataId(item.getId());
            if(null != textDetailList && textDetailList.size()>0){
                textDetailList.forEach(textDetail->{
                    CorrectData correctData = new CorrectData();
                    correctData.setElementAbb("text");
                    correctData.setStationAbb(abb);
                    correctData.setPrescriptionNum(textDetail.getIntervalId());
                    boolean isChecked = item.getChecker() == null ? false:true;
                    correctData.setChecked(isChecked);
                    correctData.setData(textDetail.getText());
                    correctDataList.add(correctData);
                });

            }
        });
        correctPacket.setDate(new Date());
        correctPacket.setDataList(correctDataList);
        return correctPacket;
    }
}
