package com.musicfinder.shared.request;

import java.util.UUID;

import com.musicfinder.shared.enums.Action;
import com.musicfinder.shared.util.Json;

public class Request {
    public String requestId;
    public Action action;
    public Object data;

    public Request() {
    }

    public static Request of(Action action, Object data) {
        Request req = new Request();
        req.requestId = UUID.randomUUID().toString();
        req.action = action;
        req.data = data;
        return req;
    }

    public <T> T dataAs(Class<T> type) {
        return Json.convert(data, type);
    }

    public String toJson() {
        return Json.toJson(this);
    }

    public static Request fromJson(String json) {
        return Json.fromJson(json, Request.class);
    }
}
