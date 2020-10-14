package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.common.entity.Result;
import com.nmefc.correctionsys.common.enums.ResultCodeEnum;
import com.nmefc.correctionsys.common.enums.ResultMsgEnum;
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

    @PostMapping(value = "/textData")
    public Result<TextDetail> getTextDetailByTextDataId(@RequestBody TextId data) {
        Result<TextDetail> textInfoResult = new Result<TextDetail>();
        //1.数据校验
        if (null == data.getId()) {

            textInfoResult.setMessage(ResultMsgEnum.ERROR_PARAMETER.getMsg());
            textInfoResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textInfoResult;
        }

        List<TextDetail> textDetailList = textDetailService.findByTextDataId(data.getId());
        if(null == textDetailList || textDetailList.size()<1){

            textInfoResult.setMessage(ResultMsgEnum.NULL_DATA.getMsg());
            textInfoResult.setCode(ResultCodeEnum.FAIL.getCode());
            return textInfoResult;
        }
        textInfoResult.setMessage(ResultMsgEnum.SUCCESS.getMsg());
        textInfoResult.setCode(ResultCodeEnum.SUCCESS.getCode());
        textInfoResult.setData(textDetailList);
        return textInfoResult;
    }
}
