package com.kroger.merchandising.magicdatareader.repository;

import com.kroger.merchandising.magicdatareader.entity.DataItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataItemRepository extends JpaRepository<DataItem, Long> {
}
