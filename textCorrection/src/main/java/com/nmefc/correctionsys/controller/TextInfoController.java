package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/textInfo")
public class TextInfoController {
    @Autowired
    private TextInfoService textInfoService;




    /**
     *@Description: （7）查询指定文字模板的最新版本号
     *@Param: [id]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/4 23:51
     */
    @GetMapping(value = "/lastVersion")
    public Integer getLastVersion(Integer id){
        //1. 数据校验
        if(id == null){return 0;}
        List<TextInfo> textInfoList =  textInfoService.getVersionListById(id);
        if(textInfoList != null || textInfoList.size() > 0){return textInfoList.get(0).gettVersion();}
        return 0;
    }
    /**
     *@Description:（8）查询指定文字模板的是否已删除
     *@Param: [id]
     *@Return: boolean
     *@Author: QuYuan
     *@Date: 2020/5/4 23:18
     */
    @GetMapping(value = "/isDelete")
    public boolean isDelete(Integer id){
        //1. 数据校验
        if(id == null){return false;}
        return textInfoService.isDelete(id);

    }
    /**
     *@Description: （6）查询指定文字模板
     *@Param: [id, version]
     *@Return: com.nmefc.correctionsys.entity.TextInfo
     *@Author: QuYuan
     *@Date: 2020/5/5 0:55
     */
    @GetMapping(value = "/textInfo")
    public TextInfo getTextInfo(Integer id, Integer version){
        //1. 数据校验
        if(id == null && version == null){return null;}
        TextInfo textInfo = textInfoService.getTextInfoByIdAndVersion(id,version);
        if(textInfo == null && textInfo.getTid() == null){return null;}
        return textInfo;
    }

}
