package com.deployd.auth_service.repository;

import com.deployd.auth_service.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Integer> {
    List<Local> findAllByOrderByIdAsc();
 //   void deleteByUserId(Integer userId);

}
