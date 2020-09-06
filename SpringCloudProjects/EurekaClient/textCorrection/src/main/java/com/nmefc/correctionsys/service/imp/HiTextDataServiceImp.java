package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.service.HiTextDataService;
import com.nmefc.correctionsys.service.TextDataService;
import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("hiTextDataService")
public class HiTextDataServiceImp extends BaseServiceImp<HiTextData,Integer,HiTextDataExample> implements HiTextDataService {
    @Autowired
    private TextInfoService textInfoService;
    @Autowired
    private TextDataService textDataService;
    @Autowired
    private HiTextDataMapper hiTextDataMapper;
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
        hiTextDataList = hiTextDataMapper.selectByExampleWithBLOBs(hiTextDataExample);
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
    /**
     *@Description:（1）实时库转历史库:将text_data中的数据全部复制到
     * hi_text_data中，并清空text_data中所有数据.
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 15:58
     */
//    [to-do]时间写入配置文件
//    暂时10分钟，后根据业务需要调整

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public Integer saveHiTextData(){
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.setOrderByClause("date ASC");
        List<TextData> textDataList = new ArrayList<>();
        textDataList = textDataService.getAll();
        List<Integer> idList = new ArrayList<>();
        textDataList.forEach(item->{
//            idList.add(item.getId());
            Integer id;
            item.setId(null);
            insertSelective(item);
        });
//        Integer y = 1/0;
        return textDataService.deleteAll();
    }
}
