package tw.com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.demo.entity.SysCodeEntity;

@Repository
public interface SysCodeRepository extends JpaRepository<SysCodeEntity, Long> {

    List<SysCodeEntity> findByGroupName(String groupName);

}
