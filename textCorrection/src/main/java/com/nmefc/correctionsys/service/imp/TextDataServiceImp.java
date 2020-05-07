package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.dao.TextDataMapper;
import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDataExample;
import com.nmefc.correctionsys.service.TextDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.soap.Text;
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
    @Override
    boolean checkParameters(TextData textData) {
        return false;
    }
/**
 *@Description:（6）根据模板id查询当日文本记录：根据tid再text_data表中查询是否有记录；
若记录数为0，返回空；
若记录数为1，则将其内容填至TextData返回；
若记录数大于1，则选其中t_version值最大的记录的内容填至TextData返回，将剩余的记录从数据库删除.
 *@Param: [id]
 *@Return: com.nmefc.correctionsys.entity.TextData
 *@Author: QuYuan
 *@Date: 2020/5/6 20:19
 */
    public TextData getTextDataById(Integer tid){
        List<TextData> textDataList = new ArrayList<>();
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.createCriteria().andTidEqualTo(tid);
        textDataExample.setOrderByClause("t_version DESC");
        //注意这里如果要返回数据库TEXT类型，必须要用WithBLOBs方法
        textDataList = textDataMapper.selectByExampleWithBLOBs(textDataExample);
        switch (textDataList.size()){
//            若记录数为0，返回空；
            case 0:
                return null;
//            若记录数为1，则将其内容填至TextData返回；
            case 1:
                return textDataList.get(0);
//            若记录数大于1，则选其中t_version值最大的记录的内容填至TextData返回，将剩余的记录从数据库删除.
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
               return textDataList.get(0);
        }

    }
    /**
     *@Description:（7）查询当日已编辑文本记录: 从text_data库中查询所有数据并返回
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextData>
     *@Author: QuYuan
     *@Date: 2020/5/7 9:00
     */
    public List<TextData> getAll(){
        List<TextData> textDataList = new ArrayList<>();
        TextDataExample textDataExample = new TextDataExample();
        textDataExample.setOrderByClause("t_version DESC");
        try{
            textDataList = textDataMapper.selectByExampleWithBLOBs(textDataExample);
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
    public Integer lastCheck(TextData textData){
        //[to-do]做完登录系统后，这里面要改为从Session中得到username
        textData.setChecker("默认审核员");
        textData.setGmtModified(new Date());
    
        try{
            return textDataMapper.updateByPrimaryKeyWithBLOBs(textData);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }

}
