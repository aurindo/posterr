package com.aurindo.posterr.application.api.post.response;

import com.aurindo.posterr.application.api.post.PostController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostResponseModelAssembler implements RepresentationModelAssembler<PostResponse, EntityModel<PostResponse>> {

    @Override
    public EntityModel<PostResponse> toModel(PostResponse postResponse) {
        return EntityModel.of(postResponse,
                linkTo(methodOn(PostController.class).getById(postResponse.getPostId())).withSelfRel());
    }

    @Override
    public CollectionModel toCollectionModel(Iterable entities) {
        return null;
    }
}
