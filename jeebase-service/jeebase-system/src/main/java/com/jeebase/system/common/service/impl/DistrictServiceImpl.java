package com.jeebase.system.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.common.entity.District;
import com.jeebase.system.common.mapper.DistrictMapper;
import com.jeebase.system.common.service.IDistrictService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jeebase
 * @since 2018-05-26
 */
@Service
public class DistrictServiceImpl extends ServiceImpl<DistrictMapper, District> implements IDistrictService {
}
