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
 * lockers (櫃子檔)
 */
@Entity
@Table(name = "lockers")
@Getter
@Setter
public class LockersEntity {

    /**
     * 置物櫃編號
     */
    @Id
    @Size(max = 10)
    @Column(name = "locker_no")
    private String lockerNo;

    /**
     * 是否可使用
     */
    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

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
