package com.novmah.restaurantmanagement.service;

import com.novmah.restaurantmanagement.dto.TableDto;
import com.novmah.restaurantmanagement.entity.status.TableStatus;

import java.util.List;

public interface TableService {

    List<TableDto> getAllTable();
    List<TableDto> getTablesByStatus(TableStatus status);

    TableDto addTable(TableDto tableDto);
    TableDto updateTableStatus(Integer id, TableStatus status);
    String deleteTableById(Integer id);

}
