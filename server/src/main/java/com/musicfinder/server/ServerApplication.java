package com.musicfinder.server;

import com.musicfinder.server.model.Users;
import com.musicfinder.server.repository.UserRepository;
import com.musicfinder.server.config.DatabaseConfig;
import java.util.Optional;

public class ServerApplication {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();

        // Dùng timestamp để mỗi lần chạy test username đều là duy nhất, không bị lỗi duplicate
        String testUsername = "user_" + System.currentTimeMillis();
        String testEmail = testUsername + "@example.com";

        System.out.println("========== 1. TEST ĐĂNG KÝ (SAVE) ==========");
        Users newUser = Users.builder()
                .username(testUsername)
                .passwordHash("hashed_password_123") // Thực tế sẽ là mật khẩu đã hash qua BCrypt
                .fullName("Nguyen Van A")
                .email(testEmail)
                .avatarUrl("http://avatar.jpg")
                .build();

        Users savedUser = userRepository.save(newUser);
        System.out.println("Thêm thành công! User ID vừa sinh: " + savedUser.getUserId());

        System.out.println("\n========== 2. TEST KIỂM TRA TỒN TẠI ==========");
        boolean existsUser = userRepository.existsByUsername(testUsername);
        boolean existsEmail = userRepository.existByEmail(testEmail);
        boolean notExists = userRepository.existsByUsername("khong_ton_tai_xyz");

        System.out.println("Kiểm tra username '" + testUsername + "' tồn tại: " + existsUser + " (Kỳ vọng: true)");
        System.out.println("Kiểm tra email '" + testEmail + "' tồn tại: " + existsEmail + " (Kỳ vọng: true)");
        System.out.println("Kiểm tra user không tồn tại: " + notExists + " (Kỳ vọng: false)");

        System.out.println("\n========== 3. TEST TÌM KIẾM / ĐĂNG NHẬP ==========");
        Optional<Users> foundByUsername = userRepository.findByUsername(testUsername);
        if (foundByUsername.isPresent()) {
            System.out.println("Tìm thấy user theo username: " + foundByUsername.get().getFullName());
        } else {
            System.err.println("Không tìm thấy user!");
        }

        Optional<Users> foundById = userRepository.findById(savedUser.getUserId());
        System.out.println("Tìm thấy theo ID: " + foundById.isPresent());

        System.out.println("\n========== 4. TEST CẬP NHẬT THÔNG TIN ==========");
        savedUser.setFullName("Nguyen Van A (Đã cập nhật)");
        savedUser.setEmail("updated_" + testEmail);
        savedUser.setAvatarUrl("http://new-avatar.jpg");

        boolean updateSuccess = userRepository.update(savedUser);
        System.out.println("Cập nhật thành công: " + updateSuccess);

        // Đọc lại từ DB để kiểm chứng dữ liệu đã đổi thật chưa
        Optional<Users> reloadedUser = userRepository.findById(savedUser.getUserId());
        reloadedUser.ifPresent(u -> {
            System.out.println("Họ tên mới trong DB: " + u.getFullName());
            System.out.println("Email mới trong DB: " + u.getEmail());
            System.out.println("Thời gian cập nhật (updated_at): " + u.getUpdatedAt());
        });

        System.out.println("\n===> TẤT CẢ TEST ĐÃ HOÀN TẤT THÀNH CÔNG!");
        DatabaseConfig.shutdown();
        System.exit(0);
    }
}
