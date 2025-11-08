# DeliGo Android

DeliGo là ứng dụng đặt món ăn và quản lý đơn hàng xây dựng bằng Java trên nền tảng Android theo mô hình MVVM. Ứng dụng hỗ trợ đăng nhập, duyệt danh mục món, quản lý giỏ hàng, đặt đơn COD và theo dõi lịch sử đơn hàng, đồng thời chuẩn bị sẵn các tầng dữ liệu để mở rộng cho đánh giá và khiếu nại.

## Kiến trúc

* **Ngôn ngữ:** Java 8+
* **Kiến trúc:** MVVM với ViewModel + LiveData
* **Tầng dữ liệu:** Repository kết hợp Room (offline cache) và Retrofit (REST API)
* **UI:** Material Components, ViewBinding, RecyclerView

Cấu trúc gói chính:

```
com.deligo.app/
 ├─ core/           # Cấu hình toàn cục (Retrofit, Room, Prefs, ViewModelFactory)
 ├─ data/           # API interface, repository và implement
 ├─ domain/         # Model thuần
 ├─ feature/        # Mỗi tính năng UI (auth, catalog, cart, order, ...)
 └─ MainActivity    # Điều hướng chính với BottomNavigationView
```

## Tính năng hiện tại

* Đăng nhập/đăng ký qua API mô phỏng, lưu token bằng `SharedPreferences`.
* Duyệt danh sách món ăn, lọc theo từ khóa, hiển thị trạng thái còn hàng.
* Quản lý giỏ hàng cục bộ với Room, tăng/giảm số lượng và tính tổng tiền.
* Đặt hàng (COD) gửi lên API và lưu lịch sử đơn vào cơ sở dữ liệu.
* Theo dõi danh sách đơn hàng cùng trạng thái cập nhật từ máy chủ.
* Bộ khung ViewModel/Repository cho đánh giá và khiếu nại (sẵn sàng tích hợp UI).

## Thiết lập & build

### 1. Yêu cầu hệ thống

* Android Studio **Giraffe** (hoặc mới hơn) với Android SDK 26+
* Java Development Kit (JDK) 11 (Android Studio bundled JDK được khuyến nghị)
* Thiết bị hoặc emulator chạy Android 8.0 (API 26) trở lên
* Kết nối Internet để tải dependency Gradle và truy cập API

### 2. Tải mã nguồn

```bash
git clone https://github.com/<your-org>/DeliGo.git
cd DeliGo
```

### 3. Mở dự án bằng Android Studio

1. Tại màn hình Welcome, chọn **Open** và trỏ tới thư mục `DeliGo`.
2. Đợi Android Studio đồng bộ Gradle và tải các dependency. Nếu có thông báo cập nhật plugin/SDK, hãy thực hiện theo hướng dẫn.

### 4. Cấu hình API backend

* Mặc định `ApiClient.BASE_URL` trỏ tới `https://api.deligo.example/`. Thay đổi hằng số này để sử dụng máy chủ của bạn.
* Nếu backend yêu cầu token khác, cập nhật logic lưu trữ tại `AppPreferences` và các repository tương ứng.

### 5. Thiết lập cơ sở dữ liệu cục bộ

Room được cấu hình tự động tạo database tên `deligo.db`. Không cần thao tác thủ công, nhưng bạn có thể xoá dữ liệu thử nghiệm bằng cách gỡ cài đặt ứng dụng trên thiết bị.

### 6. Chạy ứng dụng

1. Tạo hoặc chọn một cấu hình chạy (**Run/Debug configuration**) trong Android Studio.
2. Chọn thiết bị thật (USB debugging) hoặc AVD với API 26+ và nhấn **Run** (`Shift + F10`).
3. Đăng nhập bằng tài khoản thử nghiệm (ví dụ `user@example.com` / `password`) hoặc tích hợp API đăng ký để tạo tài khoản mới.

### 7. Kiểm tra nhanh bằng dòng lệnh (tuỳ chọn)

Để đảm bảo dự án biên dịch thành công ngoài Android Studio:

```bash
./gradlew assembleDebug
```

APK debug sẽ nằm tại `app/build/outputs/apk/debug/app-debug.apk`.

## Hướng mở rộng

* Bổ sung màn hình review/khiếu nại sử dụng sẵn `ReviewViewModel` và `ComplaintViewModel`.
* Đồng bộ đa vai trò (quản lý cửa hàng, shipper) dựa trên kiến trúc repository hiện hữu.
* Tích hợp push notification, bản đồ và thanh toán trực tuyến khi backend sẵn sàng.

## Bản quyền

MIT License.
