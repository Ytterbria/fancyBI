package com.ytterbria.fancyback.model.dto.chart;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑请求
 *
 * @author <a href="https://github.com/liytterbria">程序员鱼皮</a>
 * @from <a href="https://ytterbria.icu">编程导航知识星球</a>
 */
@Data
public class ChartEditRequest implements Serializable {
    /**
     * 图表ID
     */
    private long id;

    /**
     * 图表名称
     */
    private String chartName;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 图表类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}