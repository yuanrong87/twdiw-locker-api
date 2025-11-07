package tw.com.demo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderStatus {
    DEPOSIT("1", "寄放中"),
    DELIVERY("2", "準備配送"),
    SHIPPING("3", "配送中"),
    ARRIVED("4", "已抵達"),
    FINISH("5", "已結案");

    @Getter
    private final String code;

    @Getter
    private final String item;

    public static OrderStatus toOrderStatus(String code) {
        for (OrderStatus tmp : OrderStatus.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
