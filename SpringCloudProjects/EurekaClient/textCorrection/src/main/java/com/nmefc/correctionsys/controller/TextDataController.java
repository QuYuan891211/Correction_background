package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.common.entity.Result;
import com.nmefc.correctionsys.common.enums.ResultCodeEnum;
import com.nmefc.correctionsys.common.enums.ResultMsgEnum;
import com.nmefc.correctionsys.entity.API.CorrectPacket;
import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.entity.midModel.TextDataAndTextDetailSaveModel;
import com.nmefc.correctionsys.entity.midModel.TextId;
import com.nmefc.correctionsys.service.TextDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*",allowedHeaders ="*" )
@RestController
@RequestMapping(value = "/textData")
public class TextDataController {
    @Autowired
    private TextDataService textDataService;

    /**
     *@Description: 测试方法
     *@Param:
     *@Return:
     *@Author: QuYuan
     *@Date: 2020/7/21 13:13
     */
    //获取配置中心的值
    @Value("${user.name}")
    String name = "World";

    @RequestMapping("/getTestName")
    public String home() {
        return "Hello " + name;
    }


    /**
     * @Description:（6）根据模板id查询当日文本记录
     * @Param: [tid]
     * @Return: com.nmefc.correctionsys.entity.TextData
     * @Author: QuYuan
     * @Date: 2020/5/6 20:42
     */
    @PostMapping(value = "/textData")
    public Result<TextData> getTextDataById(@RequestBody TextId data) {
        Result<TextData> textDataResult = new Result<TextData>();
        //1.数据校验
        if (null == data.getId()) {

            textDataResult.setMessage(ResultMsgEnum.ERROR_PARAMETER.getMsg());
            textDataResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDataResult;
        }
        TextData textData = textDataService.getTextDataById(data.getId());
        if(null == textData){

            textDataResult.setMessage(ResultMsgEnum.NULL_DATA.getMsg());
            textDataResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDataResult;
        }

        textDataResult.setMessage(ResultMsgEnum.SUCCESS.getMsg());
        textDataResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        List<TextData> textDataList = new ArrayList<>();
        textDataList.add(textData);
        textDataResult.setData(textDataList);
        return textDataResult;
    }

//    /**
//     * @Description:（7）查询当日已编辑文本记录
//     * @Param: []
//     * @Return: java.util.List<com.nmefc.correctionsys.entity.TextData>
//     * @Author: QuYuan
//     * @Date: 2020/5/6 23:27
//     */
//    @GetMapping(value = "/all")
//    public List<TextData> getAllTextData() {
//        return textDataService.getAll();
//    }

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
    @PostMapping(value = "/lastCheck")
    public Result<TextData> lastCheck(@RequestBody TextId data) {
        Result<TextData> textDataResult = new Result<TextData>();
        if(null == data || data.getId() == null){
            textDataResult.setMessage(ResultMsgEnum.ERROR_PARAMETER.getMsg());
            textDataResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDataResult;
        }
        return textDataService.lastCheck(data.getId());
    }


    /**
     * @Description:（13）审核人员取消签名
     * @Param: [textData]
     * @Return: java.lang.String
     * @Author: QuYuan
     * @Date: 2020/5/7 10:04
     */
    @PostMapping(value = "/cancelLastCheck")
    public Result<TextData> cancelLastCheck(@RequestBody TextId data) {
        Result<TextData> textDataResult = new Result<TextData>();
        if(null == data || data.getId() == null){
            textDataResult.setMessage(ResultMsgEnum.ERROR_PARAMETER.getMsg());
            textDataResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDataResult;
        }
        return textDataService.cancelLastCheck(data.getId());
    }
    /**
     *@Description:（9）根据文本记录查询文本模板
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/7 14:09
     */
    @GetMapping(value = "/getTextInfo")
    public List<TextInfo> getTextInfoByTextData(){
        return textDataService.getTextInfoByTextData();
    }
//    /**
//     *@Description:（2）根据id删除文本记录
//     *@Param: [id]
//     *@Return: java.lang.Integer
//     *@Author: QuYuan
//     *@Date: 2020/5/7 14:40
//     */
//    @PostMapping(value = "deleteById")
//    public Integer deleteById(Integer id){
//        if (id == null){return 0;}
//        return textDataService.deleteById(id);
//    }
    /**
     *@Description:（1）根据模板新建文本记录
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/7 20:50
     */
    @PostMapping(value = "/save")
    public Integer saveOneTextData(TextInfo textInfo){
        if(textInfo == null || textInfo.getTid() == null){ return 0; }
        return textDataService.saveOneTextDataByTextInfo(textInfo);
    }
    /**
     *@Description: （5）更新当日文本记录
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/8 11:42
     */
    public Integer updateTextData(TextData textData, List<TextDetail> textDetailList){
        if(textData == null||textData.getId() == null){ return 0; }
        return textDataService.update(textData,textDetailList);
    }
    /**
     *@Description:（10）预报员确认完成
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 9:34
     */
    @PostMapping(value = "/checkByForecaster")
    public Result<TextDetail> checkByForecaster(@RequestBody TextDataAndTextDetailSaveModel data){
        Result<TextDetail> textDataResult = new Result<TextDetail>();
        if(data == null||data.getId() == null){
            textDataResult.setMessage(ResultMsgEnum.ERROR_PARAMETER.getMsg());
            textDataResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDataResult;
        }
        if(textDataService.checkByForecaster(data)>0){
            textDataResult.setMessage(ResultMsgEnum.SUCCESS.getMsg());
            textDataResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            return textDataResult;
        }else {
            textDataResult.setMessage(ResultMsgEnum.FAIL.getMsg());
            textDataResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDataResult;
        }
    }
    /**
     *@Description:(11)预报员取消确认
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 9:34
     */
    @PostMapping(value = "/uncheckByForecaster")
    public Integer uncheckByForecaster(TextData textData){
        if(textData == null||textData.getId() == null){ return 0; }
        return textDataService.uncheckByForecaster(textData);
    }
    /**
     *@Description:（3）获取当日全部文本（产品制作的接口方法）
     *@Param: []
     *@Return: com.nmefc.correctionsys.entity.API.CorrectPacket
     *@Author: QuYuan
     *@Date: 2021/3/8 16:01
     */
    @GetMapping(value = "/getCorrectTextToday")
    public CorrectPacket getCorrectTextToday(){
        return textDataService.getCorrectPacketToday();
    }
}
