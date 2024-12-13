package com.ytterbria.fancyback.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 图表
 *
 * @author ytterbria
 */
@TableName(value ="chart")
@Data
public class Chart implements Serializable {
    /**
     * 图标id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 上传的用户的id
     */
    private Long userId;

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

    /**
     * AI生成的图标
     */
    private String genChart;

    /**
     * AI生成的分析结果
     */
    private String genResult;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}