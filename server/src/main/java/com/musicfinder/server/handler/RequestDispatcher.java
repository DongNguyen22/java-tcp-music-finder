package com.musicfinder.server.handler;

import com.musicfinder.shared.enums.Status;
import com.musicfinder.shared.request.Request;
import com.musicfinder.shared.response.Response;

public class RequestDispatcher {

    // Nhận 1 dòng JSON từ client, luôn trả về 1 Response (không ném lỗi ra ngoài)
    public Response handle(String json) {
        Request request;
        try {
            request = Request.fromJson(json);
        } catch (IllegalArgumentException e) {
            return Response.invalid("Dữ liệu gửi lên không phải JSON hợp lệ");
        }

        if (request == null || request.getAction() == null) {
            return Response.invalid("Thiếu hoặc sai action");
        }

        try {
            switch (request.getAction()) {
                case PING:
                    return Response.success(request, "PONG");

                // Các action khác sẽ thêm dần ở đây:
                // case SEARCH_SONG: return searchHandler.searchSong(request);

                default:
                    return Response.error(request, Status.INVALID_REQUEST,
                            "Chưa hỗ trợ action: " + request.getAction());
            }
        } catch (IllegalArgumentException e) {
            // dataAs() ném lỗi khi thiếu hoặc sai dữ liệu
            return Response.error(request, Status.INVALID_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // chi tiết lỗi chỉ in ở server
            return Response.error(request, Status.SERVER_ERROR, "Lỗi xử lý phía server");
        }
    }
}