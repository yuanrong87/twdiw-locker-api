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
 * packages (包裹檔)
 */
@Entity
@Table(name = "packages")
@Getter
@Setter
public class PackagesEntity {

    /**
     * 包裹編號
     */
    @Id
    @Size(max = 20)
    @Column(name = "package_no")
    private String packageNo;

    /**
     * 手機號碼
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * 置物櫃編號
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "locker_no", nullable = false)
    private String lockerNo;

    /**
     * 是否已領取
     */
    @NotNull
    @Column(name = "is_pickup", nullable = false)
    private Boolean isPickup;

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
