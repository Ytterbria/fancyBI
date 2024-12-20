package com.ytterbria.fancyback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ytterbria.fancyback.annotation.AuthCheck;
import com.ytterbria.fancyback.common.BaseResponse;
import com.ytterbria.fancyback.common.DeleteRequest;
import com.ytterbria.fancyback.common.ErrorCode;
import com.ytterbria.fancyback.common.ResultUtils;
import com.ytterbria.fancyback.constant.UserConstant;
import com.ytterbria.fancyback.exception.BusinessException;
import com.ytterbria.fancyback.exception.ThrowUtils;
import com.ytterbria.fancyback.manager.SparkAIManager;
import com.ytterbria.fancyback.manager.YupiAIManager;
import com.ytterbria.fancyback.model.dto.chart.*;
import com.ytterbria.fancyback.model.entity.Chart;
import com.ytterbria.fancyback.model.entity.User;
import com.ytterbria.fancyback.model.vo.FancyResponse;
import com.ytterbria.fancyback.service.ChartService;
import com.ytterbria.fancyback.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ytterbria.fancyback.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *  智能图表
 *
 * @author ytterbria
 *
 */
@RestController
@RequestMapping("/chart")
@Slf4j
public class ChartController {

    @Resource
    private ChartService chartService;

    @Resource
    private UserService userService;

    @Resource
    private YupiAIManager fancyManager;

    @Resource
    private SparkAIManager sparkAIManager;

    // region 增删改查

    /**
     * 创建
     *
     * @param chartAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addChart(@RequestBody ChartAddRequest chartAddRequest, HttpServletRequest request) {
        if (chartAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartAddRequest, chart);

        User loginUser = userService.getLoginUser(request);
        chart.setUserId(loginUser.getId());

        boolean result = chartService.save(chart);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newChartId = chart.getId();
        return ResultUtils.success(newChartId);
    }

//    /**
//     * 智能分析
//     *
//     * @param generateChartByAIRequest
//     * @param request
//     * @return BaseResponse<String>
//     */
//    @PostMapping("/generate")
//    public BaseResponse<FancyResponse> generateChartByAI(@RequestPart("file") MultipartFile multipartFile,
//                                                  GenerateChartByAIRequest generateChartByAIRequest, HttpServletRequest request) {
//
//        String chartName = generateChartByAIRequest.getChartName();
//        String chartType = generateChartByAIRequest.getChartType();
//        String goal = generateChartByAIRequest.getGoal();
//        User loginUser = userService.getLoginUser(request);
//        ThrowUtils.throwIf(StringUtils.isBlank(goal), ErrorCode.PARAMS_ERROR, "调用AI接口请求参数错误, goal不能为空");
//        ThrowUtils.throwIf(StringUtils.isNotBlank(chartName) && (chartName.length() > 100) , ErrorCode.PARAMS_ERROR, "调用AI接口请求参数错误, chartName长度不能超过100");
//        final String prompt = "你是一个前端开发专家和数据分析师,接下来我会按照以下固定格式给你提供内容:\n"
//                + "分析需求: \n"
//                + "{数据分析的需求或者目标}\n"
//                + "原始数据: \n"
//                + "{csv格式的原始数据}\n"
//                + "请根据分析需求和原始数据的输入,按照以下指定的格式生成内容(此外不要有任何多余的开头,结尾,注释等):\n"
//                + "这是一个愚蠢的分割标记\n"
//                + "{前端 Echarts V5 的 option 配置对象js代码,合理的将数据可视化,不要生成任何多余的内容,只需要代码即可,生成的图表要美观,华丽}\n"
//                + "这是一个愚蠢的分割标记\n"
//                + "{明确的数据分析结论,200字以上}\n";
//
//
//        StringBuilder userInput = new StringBuilder();
//        //用户预设
//        userInput.append(prompt);
//
//        //message输入
//        userInput.append("分析需求: ")
//                .append("\n")
//                .append(goal).append("\n");
//        if (StringUtils.isNotBlank(chartType)){
//            userInput.append("生成的图表类型为: ").append(chartType).append("\n");
//        }
//        String deposedData =  ExcelUtils.ExcelToCsv(multipartFile);
//
//        userInput.append("原始数据: ").append("\n");
//        userInput.append(deposedData).append("\n");
//
//        long modelId = 1651468516836098050L;
//
//        String result = fancyManager.doChat(modelId,userInput.toString());
//
//        String[] splits = result.split("这是一个愚蠢的分割标记");
//
//        if (splits.length < 3){
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"AI生成失败");
//        }
//        String generatedChart = splits[1];
//        String generatedResult = splits[2];
//
//
//        FancyResponse fancyResponse = new FancyResponse();
//
//        fancyResponse.setGeneratedChart(generatedChart);
//        fancyResponse.setGeneratedResult(generatedResult);
//
//        Chart chart = new Chart();
//        chart.setUserId(loginUser.getId());
//        chart.setChartName(chartName);
//        chart.setGoal(goal);
//        chart.setChartData(deposedData);
//        chart.setChartType(chartType);
//        chart.setGenChart(generatedChart);
//        chart.setGenResult(generatedResult);
//        boolean saveResult = chartService.save(chart);
//        ThrowUtils.throwIf(!saveResult,ErrorCode.SYSTEM_ERROR,"图表保存失败");
//        return ResultUtils.success(fancyResponse);
//
//    }

    /**
     * 调用星火的AI接口
     * 要真正自己掌握技术本质,必须自己写代码,而不是依赖别人的代码,否则只能被别人复制,或者复制别人的
     *
     * @return BaseResponse<String>
     */
    @PostMapping("/generate/spark")
     public BaseResponse<FancyResponse> generateChartBySpark(@RequestPart("file") MultipartFile multipartFile,
                                                              GenerateChartByAIRequest generateChartByAIRequest,HttpServletRequest request ){
        String chartName = generateChartByAIRequest.getChartName();
        String chartType = generateChartByAIRequest.getChartType();
        String goal = generateChartByAIRequest.getGoal();
        User loginUser = userService.getLoginUser(request);
        ThrowUtils.throwIf(StringUtils.isBlank(goal), ErrorCode.PARAMS_ERROR, "调用AI接口请求参数错误, goal不能为空");
        ThrowUtils.throwIf(StringUtils.isNotBlank(chartName) && (chartName.length() > 100) , ErrorCode.PARAMS_ERROR, "调用AI接口请求参数错误, chartName长度不能超过100");
        final String prompt = "你是一个前端开发专家和数据分析师,接下来我会按照以下固定格式给你提供内容:\n"
                + "分析需求: \n"
                + "{数据分析的需求或者目标}\n"
                + "原始数据: \n"
                + "{csv格式的原始数据}\n"
                + "请根据分析需求和原始数据的输入,按照以下指定的格式生成内容(此外不要有任何多余的开头,结尾,注释等):\n"
                + "------\n"
                + "{前端 Echarts V5 的 option 配置对象的json代码(不包含(option=,只需要option={}里面的内容,),合理地将数据可视化,不要生成任何多余的内容,只需要代码即可,生成的图表要美观,华丽,互动性强}\n"
                + "------\n"
                + "{明确的数据分析结论,200字以上}\n";
        StringBuilder userInput = new StringBuilder();
        //message输入
        userInput.append("分析需求: ")
                .append("\n")
                .append(goal).append("\n");
        if (StringUtils.isNotBlank(chartType)){
            userInput.append("生成的图表类型为: ").append(chartType).append("\n");
        }

        userInput.append("原始数据: ").append("\n");
        userInput.append(ExcelUtils.ExcelToCsv(multipartFile)).append("\n");

        String result =sparkAIManager.SparkAnswer(prompt,userInput.toString());
        String [] splits = result.split("------");
        if (splits.length< 2){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"AI生成错误");
        }
        String generatedChart = splits[1];
        String generatedResult = splits[2];
        FancyResponse fancyResponse = new FancyResponse();
        fancyResponse.setGeneratedChart(generatedChart);
        fancyResponse.setGeneratedResult(generatedResult);
        return ResultUtils.success(fancyResponse);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteChart(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Chart oldChart = chartService.getById(id);
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldChart.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = chartService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param chartUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateChart(@RequestBody ChartUpdateRequest chartUpdateRequest) {
        if (chartUpdateRequest == null || chartUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartUpdateRequest, chart);

        long id = chartUpdateRequest.getId();
        // 判断是否存在
        Chart oldChart = chartService.getById(id);
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = chartService.updateById(chart);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Chart> getChartVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = chartService.getById(id);
        if (chart == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(chart);
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param chartQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Chart>> listChartByPage(@RequestBody ChartQueryRequest chartQueryRequest) {
        long current = chartQueryRequest.getCurrent();
        long size = chartQueryRequest.getPageSize();
        Page<Chart> chartPage = chartService.page(new Page<>(current, size),
                this.getQueryWrapper(chartQueryRequest));
        return ResultUtils.success(chartPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param chartQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Chart>> listChartVOByPage(@RequestBody ChartQueryRequest chartQueryRequest,
                                                       HttpServletRequest request) {
        long current = chartQueryRequest.getCurrent();
        long size = chartQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Chart> chartPage = chartService.page(new Page<>(current, size),
                this.getQueryWrapper(chartQueryRequest));
        return ResultUtils.success(chartPage);
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param chartQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<Chart>> listMyChartVOByPage(@RequestBody ChartQueryRequest chartQueryRequest,
                                                         HttpServletRequest request) {
        if (chartQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        chartQueryRequest.setUserId(loginUser.getId());
        long current = chartQueryRequest.getCurrent();
        long size = chartQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Chart> chartPage = chartService.page(new Page<>(current, size),
               this.getQueryWrapper(chartQueryRequest));
        return ResultUtils.success(chartPage);
    }

    // endregion

//    /**
//     * 分页搜索（从 ES 查询，封装类）
//     *
//     * @param chartQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/search/page/vo")
//    public BaseResponse<Page<Chart>> searchChartVOByPage(@RequestBody ChartQueryRequest chartQueryRequest,
//                                                         HttpServletRequest request) {
//        long size = chartQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<Chart> chartPage = chartService.searchFromEs(chartQueryRequest);
//        return ResultUtils.success(chartPage);
//    }

    /**
     * 编辑（用户）
     *
     * @param chartEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editChart(@RequestBody ChartEditRequest chartEditRequest, HttpServletRequest request) {
        if (chartEditRequest == null || chartEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartEditRequest, chart);

        User loginUser = userService.getLoginUser(request);
        long id = chartEditRequest.getId();
        // 判断是否存在
        Chart oldChart = chartService.getById(id);
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldChart.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = chartService.updateById(chart);
        return ResultUtils.success(result);
    }

    private QueryWrapper<Chart> getQueryWrapper(ChartQueryRequest chartQueryRequest) {
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<>();
        if (chartQueryRequest == null) {
            return queryWrapper;
        }
        Long id = chartQueryRequest.getId();
        Long userId = chartQueryRequest.getUserId();
        String chartName = chartQueryRequest.getChartName();
        String goal = chartQueryRequest.getGoal();
        String chartType = chartQueryRequest.getChartType();
        String sortField = chartQueryRequest.getSortField();
        String sortOrder = chartQueryRequest.getSortOrder();

        queryWrapper.eq(id != null && id > 0,"id",id);
        queryWrapper.like(StringUtils.isNotBlank(chartName),"chartName",chartName);
        queryWrapper.eq(StringUtils.isNotBlank(goal),"goal",goal);
        queryWrapper.eq(StringUtils.isNotBlank(chartType),"chart_type",chartType);
        queryWrapper.eq(userId != null && userId > 0,"user_id",userId);
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)) {
            queryWrapper.orderBy(   true, sortOrder.equalsIgnoreCase("asc"), sortField);
        }
        return queryWrapper;
    }
}
