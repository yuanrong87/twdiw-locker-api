package tw.com.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * orders (訂單檔)
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrdersEntity {

    /**
     * 訂單編號
     */
    @Id
    @Size(max = 20)
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 寄件人手機號碼
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "send_phone", nullable = false)
    private String sendPhone;

    /**
     * 寄件人姓名
     */
    @NotNull
    @Size(max = 20)
    @Column(name = "send_name", nullable = false)
    private String sendName;

    /**
     * 收件人手機號碼
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "receive_phone", nullable = false)
    private String receivePhone;

    /**
     * 收件人姓名
     */
    @NotNull
    @Size(max = 20)
    @Column(name = "receive_name", nullable = false)
    private String receiveName;

    /**
     * 1: 寄放、2: 配送
     */
    @NotNull
    @Size(max = 1)
    @Column(name = "item", nullable = false)
    private String item;

    /**
     * 置物櫃 id
     */
    @NotNull
    @Column(name = "lockers_id", nullable = false)
    private Long lockersId;

    /**
     * 是否已領取
     */
    @NotNull
    @Column(name = "is_pickup", nullable = false)
    private Boolean isPickup;

    /**
     * 已付金額
     */
    @NotNull
    @Column(name = "payment", nullable = false)
    private Integer payment;

    /**
     * 狀態
     */
    @Column(name = "status")
    private String status;

    /**
     * 建立時間
     */
    @NotNull
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    /**
     * 更新時間
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }

}
