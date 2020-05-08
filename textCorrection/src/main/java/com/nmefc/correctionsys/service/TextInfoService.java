package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.entity.TextInfoExample;
import com.nmefc.correctionsys.entity.TextInfoKey;

import java.util.List;

public interface TextInfoService extends BaseService<TextInfo,TextInfoKey,TextInfoExample> {
    /**
     *@Description:（8）查询指定文字模板的是否已删除
     *@Param: [id]
     *@Return: boolean
     *@Author: QuYuan
     *@Date: 2020/5/4 23:44
     */
    boolean isDelete(Integer id);
    /**
     *@Description:（7）查询指定文字模板的最新版本号
     *@Param: [id]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/4 23:44
     */
    List<TextInfo> getVersionListById(Integer id);

    /**
     *@Description: （6）查询指定文字模板
     *@Param: [id, version]
     *@Return: com.nmefc.correctionsys.entity.TextInfo
     *@Author: QuYuan
     *@Date: 2020/5/5 0:41
     */
    TextInfo getTextInfoByIdAndVersion(Integer id, Integer version);
    /**
     *@Description:（5）查询最新文字模板列表
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/5 0:59
     */
    List<TextInfo> getLatestTextInfoByDepartment(Integer departmentId);

    /**
     *@Description:（1）新增文字模板
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/5 10:59
     */
    Integer saveTextInfo(TextInfo textInfo);
    /**
     *@Description:（2）软删除文字模板
     *@Param: [id]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/5 11:45
     */
    Integer softDeleteByTid(TextInfo textInfo);
    /**
     *@Description:（3）修改文字模板（增量）
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/5 12:37
     */
    Integer updateTextInfo(TextInfo textInfo);

}
