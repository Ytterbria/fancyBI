package com.ytterbria.fancyback.manager;

import com.ytterbria.fancyback.common.ErrorCode;
import com.ytterbria.fancyback.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class YupiAIManager {
    @Resource
    private YuCongMingClient yuCongMingClient;

    /**
     * AI 对话接口
     * @param message 用户输入的消息
     * @return ai回复的消息
     */
    public String doChat(long modelId ,String message){
        DevChatRequest devChatRequest = new DevChatRequest();
        //modelId : 代表需要使用的AI模型
        devChatRequest.setModelId(modelId);
        //message : 包含用户输入的所有信息,包括prompt,data,goal,type等所有内容,一起拼接为一个字符串
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        if (response == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"AI接口响应错误");
        }
        return response.getData().getContent();
    }

}
