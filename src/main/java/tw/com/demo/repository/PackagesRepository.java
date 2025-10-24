package tw.com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.demo.entity.PackagesEntity;

@Repository
public interface PackagesRepository extends JpaRepository<PackagesEntity, Long> {

}
