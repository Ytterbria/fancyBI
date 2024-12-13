package com.ytterbria.fancyback.model.dto.chart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ytterbria.fancyback.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liytterbria">程序员鱼皮</a>
 * @from <a href="https://ytterbria.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChartQueryRequest extends PageRequest implements Serializable {

    /**
     * 图标id
     */
    private Long id;

    /**
     *  用户id
     */
    private Long userId;
    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图标类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}