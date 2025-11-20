package tw.com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.demo.entity.LockersEntity;

@Repository
public interface LockersRepository extends JpaRepository<LockersEntity, Long> {

    List<LockersEntity> findByLocation(String location);

    Optional<LockersEntity> findByLocationAndLockerNo(String location, String lockerNo);

    List<LockersEntity> findByIdIn(List<Long> id);

    List<LockersEntity> findByLocationAndIsActiveTrue(String location);

}
