package com.jeebase.system.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.annotation.auth.NoAuthentication;
import com.jeebase.common.base.Result;
import com.jeebase.system.common.entity.District;
import com.jeebase.system.common.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 地区字典
 * </p>
 *
 * @author jeebase
 * @since 2018-05-26
 */
@RestController
@RequestMapping("/district")
public class DistrictController {

    @Autowired
    private IDistrictService districtService;

    /**
     * 查询地区
     */
    @GetMapping("/list")
    @NoAuthentication
    public Result<List<District>> list(String code) {
        Integer parentId = 0;
        if (!StringUtils.isEmpty(code)) {
            QueryWrapper<District> ew = new QueryWrapper<>();
            ew.eq("code", code);
            District district = districtService.getOne(ew);
            if (null != district) {
                parentId = district.getId();
            }
        }
        QueryWrapper<District> ewr = new QueryWrapper<>();
        ewr.eq("parent_id", parentId);
        List<District> districtList = districtService.list(ewr);
        return new Result<List<District>>().success().put(districtList);
    }
}
