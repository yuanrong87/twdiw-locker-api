package tw.com.demo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ItemType {
    DEPOSIT("1", "寄物"),
    DELIVERY("2", "配送"),
    PICKUP("3", "取物");

    @Getter
    private final String code;

    @Getter
    private final String item;

    public static ItemType toItemType(String code) {
        for (ItemType tmp : ItemType.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
