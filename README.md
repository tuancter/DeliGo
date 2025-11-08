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

1. Mở thư mục dự án bằng Android Studio (Giraffe trở lên).
2. Đồng bộ Gradle (project đã bật `viewBinding` và khai báo các dependency chính).
3. Chạy ứng dụng trên thiết bị/emulator Android 8.0 (API 26) trở lên.

> Lưu ý: API endpoint đang trỏ tới `https://api.deligo.example/`. Thay đổi lại `ApiClient.BASE_URL` để kết nối backend thực tế.

## Hướng mở rộng

* Bổ sung màn hình review/khiếu nại sử dụng sẵn `ReviewViewModel` và `ComplaintViewModel`.
* Đồng bộ đa vai trò (quản lý cửa hàng, shipper) dựa trên kiến trúc repository hiện hữu.
* Tích hợp push notification, bản đồ và thanh toán trực tuyến khi backend sẵn sàng.

## Bản quyền

MIT License.
