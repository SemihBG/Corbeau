package com.semihbkgr.corbeau.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppStatus {

    private volatile boolean commentActivated;

    public AppStatus(@Value("${app-status.comment.activated:#{false}}") boolean commentActivated) {
        this.commentActivated = commentActivated;
        log.info("AppStatus has been initialized, CommentEnabled: {}", commentActivated);
    }

    public boolean isCommentActivated() {
        return commentActivated;
    }

    public void updateStatus(@NonNull StatusUpdateModel statusUpdateModel) {
        if (statusUpdateModel instanceof CommentStatusUpdateModel) {
            CommentStatusUpdateModel commentStatusUpdateModel = (CommentStatusUpdateModel) statusUpdateModel;
            commentActivated=commentStatusUpdateModel.activated;
            log.info("Comment status updated successfully, CommentActive: {}",commentActivated);
        } else {
            throw new IllegalArgumentException("UpdateStatusModel parameter instance class cannot be handled");
        }
    }

    //Marker interface for status update
    public interface StatusUpdateModel {

    }

    @Data
    public static class CommentStatusUpdateModel implements StatusUpdateModel {

        private boolean activated;

    }

}
