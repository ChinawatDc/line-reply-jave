package com.example.linebot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WebhookRequest {

    @JsonProperty("events")
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public static class Event {
        @JsonProperty("replyToken")
        private String replyToken;

        @JsonProperty("source")
        private Source source;

        public String getReplyToken() {
            return replyToken;
        }

        public void setReplyToken(String replyToken) {
            this.replyToken = replyToken;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }
    }

    public static class Source {
        @JsonProperty("type")
        private String type;

        @JsonProperty("groupId")
        private String groupId;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }
}
