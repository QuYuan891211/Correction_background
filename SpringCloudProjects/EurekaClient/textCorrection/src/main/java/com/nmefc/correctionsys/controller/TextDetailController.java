package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.common.entity.Result;
import com.nmefc.correctionsys.common.enums.ResultCodeEnum;
import com.nmefc.correctionsys.common.enums.ResultMsgEnum;
import com.nmefc.correctionsys.common.utils.DateTimeUtils;
import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.midModel.TextId;
import com.nmefc.correctionsys.service.TextDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
}
