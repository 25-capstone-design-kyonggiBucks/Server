package com.capstone.dto.request;

import com.capstone.domain.VideoType;
import com.capstone.domain.Voice;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateCustomVideoRequest(

/*        String happyImageURL,

        String sadImageURL,

        String surprisedImageURL,

        String angryImageURL,

        Voice voice,
        String voiceURL // voice가 커스텀일때만 사용*/
) {

/*    public static CreateCustomVideoRequest forDefaultVoice(String happyImageURL,String sadImageURL,String surprisedImageURL,
                                                    String angryImageURL) {
        return new CreateCustomVideoRequest(happyImageURL,sadImageURL,surprisedImageURL,angryImageURL,Voice.DEFAULT,null);
    }

    public static CreateCustomVideoRequest forCustomVoice(String happyImageURL,String sadImageURL,String surprisedImageURL,
                                                   String angryImageURL,String voiceURL) {
        return new CreateCustomVideoRequest(happyImageURL,sadImageURL,surprisedImageURL,angryImageURL,Voice.CUSTOM,voiceURL);
    }*/
}
