package com.ytterbria.fancyback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ytterbria.fancyback.constant.CommonConstant;
import com.ytterbria.fancyback.model.dto.chart.ChartQueryRequest;
import com.ytterbria.fancyback.model.entity.Chart;
import com.ytterbria.fancyback.service.ChartService;
import com.ytterbria.fancyback.mapper.ChartMapper;
import com.ytterbria.fancyback.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
* @author lenovo
* @description 针对表【chart(图表)】的数据库操作Service实现
* @createDate 2024-12-12 14:51:16
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{
}




