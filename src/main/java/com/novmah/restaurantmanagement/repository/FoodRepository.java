package com.novmah.restaurantmanagement.repository;

import com.novmah.restaurantmanagement.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findByNameContaining(String name, Pageable pageable);
    Page<Food> findByPriceBetween(BigDecimal low, BigDecimal high, Pageable pageable);

}
