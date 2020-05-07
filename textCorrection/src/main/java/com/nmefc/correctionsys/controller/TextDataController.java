package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDataExample;
import com.nmefc.correctionsys.service.TextDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "textData")
public class TextDataController {
    @Autowired
    private TextDataService textDataService;

    /**
     * @Description:（6）根据模板id查询当日文本记录
     * @Param: [tid]
     * @Return: com.nmefc.correctionsys.entity.TextData
     * @Author: QuYuan
     * @Date: 2020/5/6 20:42
     */
    @GetMapping(value = "/textData")
    public TextData getTextDataById(Integer tid) {
        //1.数据校验
        if (tid == null) {
            return null;
        }
        return textDataService.getTextDataById(tid);
    }

    /**
     * @Description:（7）查询当日已编辑文本记录
     * @Param: []
     * @Return: java.util.List<com.nmefc.correctionsys.entity.TextData>
     * @Author: QuYuan
     * @Date: 2020/5/6 23:27
     */
    @GetMapping(value = "/all")
    public List<TextData> getAllTextData() {
        return textDataService.getAll();
    }

    /**
     * @Description:（8）删除指定的当日已编辑文本记录
     * @Param: []
     * @Return: java.lang.Integer
     * @Author: QuYuan
     * @Date: 2020/5/7 9:29
     */
    @GetMapping(value = "/deleteOne")
    public Integer deleteById(Integer id) {
        if (id == null) {
            return 0;
        }
        return textDataService.deleteByPrimaryKey(id);
    }

    /**
     * @Description:（12）审核人员签名
     * @Param: [textData]
     * @Return: java.lang.String
     * @Author: QuYuan
     * @Date: 2020/5/7 10:04
     */
    @GetMapping(value = "/lastCheck")
    public String lastCheck(TextData textData) {
        if (textData == null || textData.getGmtCreate() == null ||textData.getGmtModified() == null|| textData.gettVersion() == null || textData.getTid() == null || textData.getId() == 0 || textData.getIsok() == null) {
            return "传入参数有误";
        }
        if (textData.getIsok() == false) {
            return "预报员未确认完成";
        }
        if (0 == textDataService.lastCheck(textData)) {
            return "确认失败";
        }
        return "确认成功";
    }


    /**
     * @Description:（13）审核人员取消签名
     * @Param: [textData]
     * @Return: java.lang.String
     * @Author: QuYuan
     * @Date: 2020/5/7 10:04
     */
    @GetMapping(value = "/cancelLastCheck")
    public String cancelLastCheck(TextData textData) {
        if (textData == null || textData.getGmtCreate() == null ||textData.getGmtModified() == null || textData.gettVersion() == null || textData.getTid() == null || textData.getId() == 0 || textData.getIsok() == null) {
            return "传入参数有误";
        }
        if (0 == textDataService.cancelLastCheck(textData)) {
            return "审核人员身份验证错误";
        }
        return "取消成功";
    }
}
