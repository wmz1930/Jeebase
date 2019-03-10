package com.jeebase.system.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.Constant;
import com.jeebase.system.common.dto.DictInfo;
import com.jeebase.system.common.entity.Dict;
import com.jeebase.system.common.mapper.DictMapper;
import com.jeebase.system.common.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author jeebase
 * @since 2018-10-28
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Autowired
    DictMapper dictMapper;

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public boolean createDict(Dict dict) {
        QueryWrapper<Dict> ew = new QueryWrapper<>();
        ew.eq("dict_code", dict.getDictCode()).eq("parent_id", Constant.PARENT_ID);
        List<Dict> dictList = this.list(ew);
        if (!CollectionUtils.isEmpty(dictList)) {
            throw new BusinessException("字典值重复");
        }
        boolean result = this.save(dict);
        return result;
    }

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public boolean updateDict(Dict dict) {
        QueryWrapper<Dict> ew = new QueryWrapper<>();
        ew.eq("dict_code", dict.getDictCode()).eq("parent_id", Constant.PARENT_ID);
        List<Dict> dictList = this.list(ew);
        if (!CollectionUtils.isEmpty(dictList)) {
            throw new BusinessException("字典值重复");
        }
        boolean result = this.updateById(dict);
        return result;
    }

    @Override
    @CacheEvict(value = "dict", allEntries = true)
    public boolean deleteDict(Integer dictId) {
        QueryWrapper<Dict> wpd = new QueryWrapper<Dict>();
        wpd.and(e -> e.eq("id", dictId).or().eq("parent_id", dictId));
        boolean result = this.remove(wpd);
        return result;
    }

    @Override
    @Cacheable(value = "dict", key = "'parent_code_'.concat(#dictCode)")
    public List<Dict> queryDictListByPanentCode(String dictCode) {
        QueryWrapper<Dict> parentEw = new QueryWrapper<>();
        parentEw.eq("dict_status", "1").eq("dict_code", dictCode);
        Dict dict = this.getOne(parentEw);
        List<Dict> dictList = null;
        if(null != dict)
        {
            QueryWrapper<Dict> ew = new QueryWrapper<>();
            ew.eq("dict_status", "1").eq("parent_id", dict.getId());
            dictList = this.list(ew);
        }
        
        return dictList;
    }

    @Override
    public List<DictInfo> queryDictTreeByPanentId(Integer parentId) {
        if (null == parentId) {
            parentId = 0;
        }
        List<DictInfo> dictList = dictMapper.queryDictTreeProc(parentId);
        List<DictInfo> dicts = new ArrayList<DictInfo>();
        Map<Integer, DictInfo> dictMap = new HashMap<Integer, DictInfo>();
        // 组装子父级目录
        for (DictInfo dict : dictList) {
            dictMap.put(dict.getId(), dict);
        }
        for (DictInfo dict : dictList) {
            Integer treePId = dict.getParentId();
            DictInfo pTreev = dictMap.get(treePId);
            if (null != pTreev && !dict.equals(pTreev)) {
                List<DictInfo> nodes = pTreev.getChildren();
                if (null == nodes) {
                    nodes = new ArrayList<DictInfo>();
                    pTreev.setChildren(nodes);
                }
                nodes.add(dict);
            } else {
                dicts.add(dict);
            }
        }
        return dicts;
    }
}
