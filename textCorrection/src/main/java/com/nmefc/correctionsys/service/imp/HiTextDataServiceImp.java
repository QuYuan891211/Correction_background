package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.dao.TextInfoMapper;
import com.nmefc.correctionsys.entity.HiTextData;
import com.nmefc.correctionsys.entity.HiTextDataExample;
import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.entity.TextInfoKey;
import com.nmefc.correctionsys.service.HiTextDataService;
import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("hiTextDataService")
public class HiTextDataServiceImp extends BaseServiceImp<HiTextData,Integer,HiTextDataExample> implements HiTextDataService {
    @Autowired
    private TextInfoService textInfoService;
    @Override
    boolean checkParameters(HiTextData hiTextData) {
        return false;
    }
    /**
     *@Description:（4）按日期查询历史文本:通过日期，在hi_text_data表中选择对应的文字记录
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.HiTextData>
     *@Author: QuYuan
     *@Date: 2020/5/14 14:14
     */
    public List<HiTextData> getHiTextDataByDate(Date start, Date end){
        HiTextDataExample hiTextDataExample = new HiTextDataExample();
        List<HiTextData> hiTextDataList = new ArrayList<>();
        hiTextDataExample.createCriteria().andDateBetween(start,end);
        hiTextDataList = selectByExample(hiTextDataExample);
        return hiTextDataList;
    }
    /**
     *@Description:（5）按日期查询历史文本相关的模板:通过日期，调用按日期查询历史文本获得List<TextData>，
     * 按tid、t_version去重，调用查询指定文字模板获得List<TextInfo>
     *@Param: [start, end]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/14 14:39
     */
    public List<TextInfo> getTextInfoByDate(Date start, Date end){
        List<HiTextData> hiTextDataList = getHiTextDataByDate(start,end);
        List<TextInfo> textInfoList = new ArrayList<>();
        List<HiTextData> distList = new ArrayList<>();
        if(hiTextDataList != null || hiTextDataList.size()>0){
            //tid和tVersion去重
            distList = hiTextDataList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o->o.getTid () + ";" + o.gettVersion()))),ArrayList::new));
        }
        distList.forEach(o->{
            TextInfoKey textInfoKey = new TextInfoKey();
            textInfoKey.setTid(o.getTid());
            textInfoKey.settVersion(o.gettVersion());
            TextInfo textInfo = textInfoService.selectByPrimaryKey(textInfoKey);
            if(textInfo !=null){
                textInfoList.add(textInfo);
            }
        });
        return textInfoList;
    }
    
}
