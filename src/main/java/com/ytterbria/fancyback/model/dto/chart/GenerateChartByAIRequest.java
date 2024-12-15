package com.ytterbria.fancyback.model.dto.chart;

import lombok.Data;

@Data
public class GenerateChartByAIRequest {
    /**
     *  图标名称
     */
    private String chartName;

    /**
     *  图表目标
     */
    private String goal;

    /**
     *  图表类型
     */
    private String chartType;
}
