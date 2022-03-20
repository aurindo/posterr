package com.aurindo.posterr.application.api.post.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @NotBlank
    @Size(max = 100)
    private String userId;

    @NotBlank
    @Size(max = 777)
    private String content;

    @NotBlank
    @Size(max = 100)
    private String type;

    @Size(max = 100)
    private String parentId;

}
