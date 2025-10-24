package tw.com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.demo.entity.LockersEntity;

@Repository
public interface LockersRepository extends JpaRepository<LockersEntity, Long> {

}
