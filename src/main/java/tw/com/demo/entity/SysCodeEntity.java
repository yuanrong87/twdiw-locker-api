package tw.com.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * sys_code (系統管理檔)
 */
@Entity
@Table(name = "sys_code")
@Getter
@Setter
public class SysCodeEntity {

    /**
     * 主建值
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 群組名稱
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "group_name", nullable = false)
    private String groupName;

    /**
     * sys_key
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "sys_key", nullable = false)
    private String sysKey;

    /**
     * sys_value
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "sys_value", nullable = false)
    private String sysValue;

    /**
     * 備註
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "memo", nullable = false)
    private String memo;

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

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

}
