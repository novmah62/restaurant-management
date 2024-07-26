package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.dto.response.OrderResponse;
import com.novmah.restaurantmanagement.entity.Order;
import com.novmah.restaurantmanagement.entity.status.OrderStatus;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.mapper.TableMapper;
import com.novmah.restaurantmanagement.dto.TableDto;
import com.novmah.restaurantmanagement.entity.DiningTable;
import com.novmah.restaurantmanagement.entity.status.TableStatus;
import com.novmah.restaurantmanagement.repository.TableRepository;
import com.novmah.restaurantmanagement.service.TableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final TableMapper tableMapper;

    public TableServiceImpl(TableRepository tableRepository, TableMapper tableMapper) {
        this.tableRepository = tableRepository;
        this.tableMapper = tableMapper;
    }

    @Override
    public TableDto addTable(TableDto tableDto) {
        DiningTable diningTable = tableRepository.save(tableMapper.map(tableDto));
        return tableMapper.map(diningTable);
    }

    @Override
    public TableDto updateTableStatus(Integer id, TableStatus tableStatus) {
        DiningTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "table ID", id.toString()));
        table.setStatus(tableStatus);
        tableRepository.save(table);
        return tableMapper.map(table);
    }

    @Override
    public String deleteTableById(Integer id) {
        tableRepository.deleteById(id);
        return "delete successfully!!!";
    }

    @Override
    public List<TableDto> getAllTable() {
        List<DiningTable> diningTables = tableRepository.findAll();
        return diningTables.stream().map(tableMapper::map).toList();
    }

    @Override
    public List<TableDto> getTablesByStatus(TableStatus status) {
        List<DiningTable> diningTables = tableRepository.findAllByStatus(status);
        return diningTables.stream().map(tableMapper::map).toList();
    }

}
