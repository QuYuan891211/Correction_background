package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.common.utils.DateTimeUtils;
import com.nmefc.correctionsys.dao.TextDetailMapper;
import com.nmefc.correctionsys.entity.*;
import com.nmefc.correctionsys.entity.API.CorrectData;
import com.nmefc.correctionsys.service.TextDataService;
import com.nmefc.correctionsys.service.TextDetailService;
import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service("textDetailService")
public class TextDetailServiceImp extends BaseServiceImp<TextDetail,Integer,TextDetailExample> implements TextDetailService {
    @Autowired
    private TextDetailMapper textDetailMapper;
    @Autowired
    private TextInfoService textInfoService;
    @Autowired
    private TextDataService textDataService;

    @Override
    boolean checkParameters(TextDetail textDetail) {
        return false;
    }

    @Transactional
    @Override
    public Integer updateTextDetail(TextDetail textDetail) {
        return textDetailMapper.updateByPrimaryKeyWithBLOBs(textDetail);
    }

    @Override
    public List<TextDetail> findByTextDataId(Integer id) {
        TextDetailExample textDetailExample = new TextDetailExample();
        textDetailExample.createCriteria().andTextDataIdEqualTo(id);
        textDetailExample.setOrderByClause("interval_id ASC");
        return textDetailMapper.selectByExampleWithBLOBs(textDetailExample);
    }

    @Override
    public List<TextDetail> getLastDayTextDetailById(Integer id) {
        List<TextInfo> textInfoList =  textInfoService.getVersionListById(id);
        TextInfoKey textInfoKey = new TextInfoKey();
        textInfoKey.setTid(textInfoList.get(0).getTid());
        textInfoKey.settVersion(textInfoList.get(0).gettVersion());
        TextData textData = textDataService.getLastDayTextData(textInfoKey);
        if(null != textData && textData.getId() != null){
           return this.findByTextDataId(textData.getId());
        }
//        TextDetailExample textDetailExample = new TextDetailExample();
//        DateTimeUtils.initDateByDay();
//        textDetailExample.or().andGmtModifiedBetween(DateTimeUtils.initLastDateByDay(),DateTimeUtils.initDateByDay()).andTextDataIdEqualTo(id);
//        textDetailExample.setOrderByClause("interval_id ASC");
        return null;
    }
    /**
     *@Description: 接收文本记录,接收文本自动生成工程（nc2word)传来的文本，并转存入数据库
     *@Param: [correctDataList]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2021/4/17 10:05
     */
    @Transactional
    @Override
    public Boolean saveTextDetailByAutoProject(List<CorrectData> correctDataList, Date date) {
        Boolean isSucceed = false;
        //0. 遍历接收的数据，按照相同的stationAbb进行分组，
//        HashMap<String, List<CorrectData>> map = new HashMap<String, List<CorrectData>>();
        List<List<CorrectData>> groupList = new ArrayList<>();
        correctDataList.stream().collect(Collectors.groupingBy(CorrectData::getStationAbb,Collectors.toList())).forEach(
                (stationAbb, correctDataListByName)->{
                    groupList.add(correctDataListByName);
                }
        );



        //1. 查询所有TEXTInfo，获取列表
        TextInfoExample textInfoExample = new TextInfoExample();
        textInfoExample.createCriteria().andIsDeleteLessThan(true);
        List<TextInfo> textInfoList = textInfoService.selectByExample(textInfoExample);

        //2. 遍历groupList, 如果某元素的tName为空，返回； 如果是和现有TextInfo同名(tName)，则给同名（tName）新增一个版本；（此方案不允许用户新增模板，必须是现有模板更新）
        groupList.forEach(correctDataGroupedList -> {
            //[to-do]需验证stationAbb相同的情况下，intervalid不能重复



            String newTextInfoName = correctDataGroupedList.get(0).getStationAbb();
            //2.1. 如果是和现有TextInfo同名(tName)，则给同名（tName）新增一个版本；
            boolean flag = textInfoList.stream().anyMatch((textInfo) -> textInfo.gettName().equals(newTextInfoName));
            if(flag) {
                //2.1.找出未删除且版本最新的，获取tid和版本号
                TextInfoExample lastVersionQuery = new TextInfoExample();
                lastVersionQuery.setOrderByClause("t_version DESC");
                lastVersionQuery.createCriteria().andIsDeleteEqualTo(false).andTNameEqualTo(newTextInfoName);
                TextInfo lastVersionTextInfo = textInfoService.selectByExample(lastVersionQuery).get(0);
                //2.2 新增一条TextInfo(已更改，现不需要新增)
//                TextInfo updateTextInfo = new TextInfo();
//                updateTextInfo.setGmtModified(new Date());
//                updateTextInfo.setGmtCreate(new Date());
//                updateTextInfo.settVersion(lastVersionTextInfo.gettVersion());
//                updateTextInfo.setTid(lastVersionTextInfo.getTid());
//                updateTextInfo.setIsDelete(false);
//                updateTextInfo.settAbbreviation("测试写入API");
//                try {
//                    textInfoService.(updateTextInfo);
//                }catch (Exception e){
//                    e.printStackTrace();
//
//                }
                ;
                //3.  并根据刚才获取的获取tid和版本号新建一条TextData
//                TextInfoExample newTextInfoQuery = new TextInfoExample();
//                newTextInfoQuery.setOrderByClause("t_version DESC");
//                newTextInfoQuery.createCriteria().andIsDeleteEqualTo(false).andTNameEqualTo(newTextInfoName);
//                TextInfo newTextInfo = textInfoService.selectByExample(lastVersionQuery).get(0);
                try{
                    textDataService.saveOneTextDataByTextInfo(lastVersionTextInfo, date);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //4. 找到新增（更新）的那条TextData记录，新增（更新）多条TextDetail记录

                TextDataExample textDataExample = new TextDataExample();
                textDataExample.createCriteria().andTVersionEqualTo(lastVersionTextInfo.gettVersion()).andTidEqualTo(lastVersionTextInfo.getTid());
                textDataExample.setOrderByClause("gmt_modified DESC");
                List<TextData> textDataList = textDataService.selectByExample(textDataExample);
                if (null == textDataList) {
                    return;
                }
                //4.1 获取id
                TextData textData = textDataList.get(0);
                //4.2 找到是否有现有的Detail
                TextDetailExample textDetailExample = new TextDetailExample();
                textDetailExample.createCriteria().andTextDataIdEqualTo(textData.getId());
                List<TextDetail> textDetailList = selectByExample(textDetailExample);

                if(null==textDetailList||textDetailList.size() < 1){
                //4.3 没有记录，新增
                    correctDataGroupedList.forEach(correctData -> {

                        TextDetail textDetail = new TextDetail();
                        textDetail.setGmtCreate(new Date());
                        textDetail.setGmtModified(new Date());
                        textDetail.setText(correctData.getData());
                        textDetail.setIntervalId(correctData.getPrescriptionNum());
                        textDetail.setTextDataId(textData.getId());
                        try{
                            this.insertSelective(textDetail);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });

                }else {
                    //4.4 已有记录，更新
                    correctDataGroupedList.forEach(correctData -> {
                        TextDetailExample textDetailExampleUpdate = new TextDetailExample();
                        textDetailExampleUpdate.createCriteria().andTextDataIdEqualTo(textData.getId()).andIntervalIdEqualTo(correctData.getPrescriptionNum());

                        TextDetail textDetail = new TextDetail();
                        textDetail.setGmtModified(new Date());
                        textDetail.setText(correctData.getData());

                        try{
                            this.updateByExampleSelective(textDetail, textDetailExampleUpdate);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                }

            }
        });
        isSucceed = true;
        return isSucceed;
    }
}
