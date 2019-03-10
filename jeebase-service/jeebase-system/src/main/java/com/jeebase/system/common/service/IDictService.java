package com.jeebase.system.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.common.dto.DictInfo;
import com.jeebase.system.common.entity.Dict;

import java.util.List;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author jeebase
 * @since 2018-10-28
 */
public interface IDictService extends IService<Dict> {
    
    /**
     * 创建数据字典
     * 
     * @param dict
     * @param
     * @return
     */
    boolean createDict(Dict dict);

    /**
     * 更新数据字典
     * 
     * @param dict
     * @param
     * @return
     */
    boolean updateDict(Dict dict);
    
    /**
     * 删除数据字典
     * @param dictId
     * @return
     */
    boolean deleteDict(Integer dictId);
    
    /**
     * 查询字典列表值
     * @param dictCode
     * @return
     */
    List<Dict> queryDictListByPanentCode(String dictCode);
    
    /**
     * 查询字典列表树
     * @param parentId
     * @return
     */
    List<DictInfo> queryDictTreeByPanentId(Integer parentId);
}
