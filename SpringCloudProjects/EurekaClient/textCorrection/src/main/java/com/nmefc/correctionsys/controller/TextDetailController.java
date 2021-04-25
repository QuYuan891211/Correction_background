package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.common.entity.Result;
import com.nmefc.correctionsys.common.enums.ResultCodeEnum;
import com.nmefc.correctionsys.common.enums.ResultMsgEnum;
import com.nmefc.correctionsys.common.utils.DateTimeUtils;
import com.nmefc.correctionsys.entity.API.CorrectData;
import com.nmefc.correctionsys.entity.API.CorrectPacket;
import com.nmefc.correctionsys.entity.API.ResponseInfo;
import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.midModel.TextId;
import com.nmefc.correctionsys.service.TextDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders ="*" )
@RestController
@RequestMapping(value = "/textDetail")
public class TextDetailController {
    @Autowired
    private TextDetailService textDetailService;

    @PostMapping(value = "/getTextDetail")
    public Result<TextDetail> getTextDetailByTextDataId(@RequestBody TextId data) {
        Result<TextDetail> textDetailResult = new Result<TextDetail>();
        //1.数据校验
        if (null == data.getId()) {

            textDetailResult.setMessage(ResultMsgEnum.ERROR_PARAMETER.getMsg());
            textDetailResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDetailResult;
        }

        List<TextDetail> textDetailList = textDetailService.findByTextDataId(data.getId());
        if(null == textDetailList || textDetailList.size()<1){

            textDetailResult.setMessage(ResultMsgEnum.NULL_DATA.getMsg());
            textDetailResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDetailResult;
        }
        textDetailResult.setMessage(ResultMsgEnum.SUCCESS.getMsg());
        textDetailResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        textDetailResult.setData(textDetailList);
        return textDetailResult;
    }
    @PostMapping(value = "/lastDay")
    public Result<TextDetail> getLastDayDetail(@RequestBody TextId data){
        Result<TextDetail> textDetailResult = new Result<TextDetail>();
        //1.数据校验
        if (null == data.getId()) {

            textDetailResult.setMessage(ResultMsgEnum.ERROR_PARAMETER.getMsg());
            textDetailResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDetailResult;
        }

        List<TextDetail> textDetailList = textDetailService.getLastDayTextDetailById(data.getId());
        if(null == textDetailList || textDetailList.size()<1){

            textDetailResult.setMessage(ResultMsgEnum.NULL_DATA.getMsg());
            textDetailResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textDetailResult;
        }
        textDetailResult.setMessage(ResultMsgEnum.SUCCESS.getMsg());
        textDetailResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        textDetailResult.setData(textDetailList);
        return textDetailResult;
    }
    /**
     *@Description:（3）接收文本记录,接收文本自动生成工程（nc2word)传来的文本，并转存入数据库
     *@Param: [correctPacket]
     *@Return: java.lang.String
     *@Author: QuYuan
     *@Date: 2021/4/17 9:43
     */
    @PostMapping(value = "/autoSaveText")
    public ResponseInfo autoSaveTextDetail(@RequestBody CorrectPacket correctPacket){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setServerTime(new Date());

        //1. 判断请求是否为空
        if(null == correctPacket){
            responseInfo.setSucceed(false);
            responseInfo.setMsg("失败，请求为空");
            responseInfo.setCode("500");
            return responseInfo;
        }
        List<CorrectData> correctDataList = correctPacket.getDataList();
        if (correctPacket.getDate() == null || !DateTimeUtils.isToday(correctPacket.getDate())){
            responseInfo.setSucceed(false);
            responseInfo.setMsg("失败，date参数为空或date参数不为今天日期");
            responseInfo.setCode("500");
            return responseInfo;
        }
        if(correctDataList == null || correctDataList.size() < 1){
            responseInfo.setSucceed(false);
            responseInfo.setMsg("失败，dataList参数为空");
            responseInfo.setCode("500");
            return responseInfo;
        }

        if(textDetailService.saveTextDetailByAutoProject(correctDataList, correctPacket.getDate())){
            responseInfo.setSucceed(true);
            responseInfo.setMsg("成功");
            responseInfo.setCode("200");
            return responseInfo;
        }else {
            responseInfo.setSucceed(false);
            responseInfo.setMsg("失败，调用接口失败，请联系管理员");
            responseInfo.setCode("500");
            return responseInfo;
        }

    }

}
