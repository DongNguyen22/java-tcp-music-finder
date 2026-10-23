package com.musicfinder.server.handler;

import java.util.Optional;

import com.musicfinder.server.service.ArtistService;
import com.musicfinder.server.service.AuthService;
import com.musicfinder.server.service.HistoryService;
import com.musicfinder.server.service.StatisticsService;
import com.musicfinder.shared.enums.Status;
import com.musicfinder.shared.request.Request;
import com.musicfinder.shared.request.payload.ArtistPayload;
import com.musicfinder.shared.request.payload.EditProfilePayload;
import com.musicfinder.shared.request.payload.LoginPayload;
import com.musicfinder.shared.request.payload.RegisterPayload;
import com.musicfinder.shared.request.payload.RemoveRowSearchHistory;
import com.musicfinder.shared.request.payload.StatisticsPayload;
import com.musicfinder.shared.response.Response;
import com.musicfinder.shared.response.payload.UserData;
import com.musicfinder.shared.response.payload.ViewArtistDetailData;
import com.musicfinder.shared.response.payload.ViewArtistSongsData;

public class RequestDispatcher {

    // Service tạo 1 lần, dùng chung cho mọi Client (service không giữ dữ liệu riêng
    // của request)
    private final StatisticsService statisticsService = new StatisticsService();
    private final ArtistService artistService = new ArtistService();
    private final HistoryService historyService = new HistoryService();
    private final AuthService authService = new AuthService();

    public Response handle(String json) {
        return handle(json, null);
    }

    // Nhận 1 dòng JSON từ client, luôn trả về 1 Response (không ném lỗi ra ngoài)
    // userId = id của user đã đăng nhập trên kết nối này, null nếu chưa đăng nhập
    public Response handle(String json, Long userId) {
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
                // ======= Xác thực ========
                case REGISTER: {
                    RegisterPayload payload = request.dataAs(RegisterPayload.class);
                    UserData data = authService.register(payload);
                    return Response.success(request, data);
                }
                case LOGIN: {
                    LoginPayload payload = request.dataAs(LoginPayload.class);
                    UserData data = authService.login(payload);
                    return Response.success(request, data);
                }
                case GET_PROFILE: {
                    // Tạm thời lấy username từ field hoặc session
                    String username = request.getData().path("username").asText("");
                    UserData data = authService.getProfile(username);
                    return Response.success(request, data);
                }
                case UPDATE_PROFILE: {
                    String username = request.getData().path("username").asText("");
                    EditProfilePayload payload = request.dataAs(EditProfilePayload.class);
                    UserData data = authService.updateProfile(username, payload);
                    return Response.success(request, data);
                }
                
                // ===== Thống kê =====
                case GET_TOP_SONGS:
                    return Response.success(request, statisticsService.getTopSongs(readLimit(request)));

                case GET_TOP_ARTISTS:
                    return Response.success(request, statisticsService.getTopArtists(readLimit(request)));

                // ===== Ca sĩ =====
                case VIEW_ARTIST_DETAIL: {
                    ArtistPayload payload = request.dataAs(ArtistPayload.class);
                    Optional<ViewArtistDetailData> result = artistService.viewArtistDetail(payload.artistID());
                    if (result.isEmpty()) {
                        return Response.error(request, Status.NOT_FOUND, "Không tìm thấy ca sĩ");
                    }
                    return Response.success(request, result.get());
                }

                case VIEW_ARTIST_SONGS: {
                    ArtistPayload payload = request.dataAs(ArtistPayload.class);
                    Optional<ViewArtistSongsData> result = artistService.viewArtistSongs(payload.artistID());
                    if (result.isEmpty()) {
                        return Response.error(request, Status.NOT_FOUND, "Không tìm thấy ca sĩ");
                    }
                    return Response.success(request, result.get());
                }

                // ===== Lịch sử tìm kiếm (phải đăng nhập) =====
                case GET_HISTORY:
                    if (userId == null) {
                        return Response.error(request, Status.UNAUTHORIZED, "Bạn chưa đăng nhập");
                    }
                    return Response.success(request, historyService.getHistory(userId));

                case REMOVE_ROW_HISTORY: {
                    if (userId == null) {
                        return Response.error(request, Status.UNAUTHORIZED, "Bạn chưa đăng nhập");
                    }
                    RemoveRowSearchHistory payload = request.dataAs(RemoveRowSearchHistory.class);
                    boolean removed = historyService.removeRow(userId, payload.historyID());
                    if (!removed) {
                        return Response.error(request, Status.NOT_FOUND, "Không tìm thấy dòng lịch sử");
                    }
                    return Response.success(request, null);
                }

                case CLEAR_HISTORY:
                    if (userId == null) {
                        return Response.error(request, Status.UNAUTHORIZED, "Bạn chưa đăng nhập");
                    }
                    historyService.clearHistory(userId);
                    return Response.success(request, null);

                // Các action khác sẽ thêm dần ở đây:
                // case SEARCH_SONG: return searchHandler.searchSong(request);

                default:
                    return Response.error(request, Status.INVALID_REQUEST,
                            "Chưa hỗ trợ action: " + request.getAction());
            }
        } catch (IllegalArgumentException e) {
            return Response.error(request, Status.INVALID_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(request, Status.SERVER_ERROR, "Lỗi xử lý phía server");
        }
    }

    private int readLimit(Request request) {
        if (request.getData() == null || request.getData().isNull()) {
            return 0;
        }
        return request.dataAs(StatisticsPayload.class).limit();
    }
}