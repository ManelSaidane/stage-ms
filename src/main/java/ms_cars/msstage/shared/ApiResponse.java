package ms_cars.msstage.shared;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final String message;

    private ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    private ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.message = null;
    }

    private ApiResponse(boolean success, String message) {
        this.success = success;
        this.data = null;
        this.message = message;
    }


    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data);
    }


    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }


    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }
}
