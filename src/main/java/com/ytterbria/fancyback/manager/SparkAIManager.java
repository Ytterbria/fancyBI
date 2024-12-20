package com.ytterbria.fancyback.manager;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SparkAIManager {
    @Resource
    private SparkClient sparkClient;

    public String SparkAnswer(String prompt,String userInput){
      List<SparkMessage> Inputs = new ArrayList<>();

      Inputs.add(SparkMessage.systemContent(prompt));
      Inputs.add(SparkMessage.userContent(userInput));

        SparkRequest sparkRequest = SparkRequest.builder()
                .messages(Inputs)
                .maxTokens(4096)
                .temperature(0.2)
                .apiVersion(SparkApiVersion.V4_0)
                .build();

        try {
            // 同步调用
            SparkSyncChatResponse response = sparkClient.chatSync(sparkRequest);
            return response.getContent();
        } catch (Exception e) {
            return "Sorry, Spark failed to answer your question.";
        }
    }
}
