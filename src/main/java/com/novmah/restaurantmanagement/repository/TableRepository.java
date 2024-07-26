package com.novmah.restaurantmanagement.repository;

import com.novmah.restaurantmanagement.entity.DiningTable;
import com.novmah.restaurantmanagement.entity.status.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<DiningTable, Integer> {
    List<DiningTable> findAllByStatus(TableStatus status);


}
