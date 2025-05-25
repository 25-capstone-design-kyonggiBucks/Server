package com.capstone.dto.request;

import com.capstone.domain.FacialExpression;
import com.capstone.domain.VideoType;
import com.capstone.domain.Voice;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateCustomVideoRequest(

        String title,

        String happyImageURL,

        String sadImageURL,

        String surprisedImageURL,

        String angryImageURL,

        Voice voice,
        String voiceURL // voice가 커스텀일때만 사용
) {

    private static CreateCustomVideoRequest forDefaultVoice(String title,String happyImageURL,String sadImageURL,String surprisedImageURL,
                                                    String angryImageURL) {
        return new CreateCustomVideoRequest(title,happyImageURL,sadImageURL,surprisedImageURL,angryImageURL,Voice.DEFAULT,null);
    }

    private static CreateCustomVideoRequest forCustomVoice(String title,String happyImageURL,String sadImageURL,String surprisedImageURL,
                                                   String angryImageURL,String voiceURL) {
        return new CreateCustomVideoRequest(title,happyImageURL,sadImageURL,surprisedImageURL,angryImageURL,Voice.CUSTOM,voiceURL);
    }

    public static CreateCustomVideoRequest forDefaultVoice(Map<FacialExpression,String> expressionUrlMap,String title) {
        String happyUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.HAPPY))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 기쁨 표정이 없습니다."));
        String sadUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.SAD))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 슬픈 표정이 없습니다."));
        String surprisedUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.SURPRISED))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 놀란 표정이 없습니다."));
        String angryUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.ANGRY))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 화난 표정이 없습니다."));

        return CreateCustomVideoRequest.forDefaultVoice(title,happyUrl,sadUrl,surprisedUrl,angryUrl);
    }

    public static CreateCustomVideoRequest forCustomVoice(Map<FacialExpression,String> expressionUrlMap,String title,String voiceURL) {
        String happyUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.HAPPY))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 기쁨 표정이 없습니다."));
        String sadUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.SAD))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 슬픈 표정이 없습니다."));
        String surprisedUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.SURPRISED))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 놀란 표정이 없습니다."));
        String angryUrl = Optional.ofNullable(expressionUrlMap.get(FacialExpression.ANGRY))
                .orElseThrow(() -> new IllegalStateException("[ERROR] 화난 표정이 없습니다."));

        return CreateCustomVideoRequest.forCustomVoice(title,happyUrl,sadUrl,surprisedUrl,angryUrl,voiceURL);
    }
}
