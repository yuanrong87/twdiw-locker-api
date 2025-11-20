package tw.com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.demo.entity.OrdersEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

    List<OrdersEntity> findByReceivePhoneAndReceiveNameAndStatusInAndLockersIdIn(String receivePhone,
            String receiveName, List<String> status, List<Long> lockersId);

}
