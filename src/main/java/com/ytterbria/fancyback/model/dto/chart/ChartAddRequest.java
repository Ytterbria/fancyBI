package com.ytterbria.fancyback.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author ytterbria
 *
 */
@Data
public class ChartAddRequest implements Serializable {
    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图标数据
     */
    private String chartData;

    /**
     * 图标类型
     */
    private String chartType;


    private static final long serialVersionUID = 1L;
}