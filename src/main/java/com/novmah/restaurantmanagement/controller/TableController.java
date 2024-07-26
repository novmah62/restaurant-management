package com.novmah.restaurantmanagement.controller;

import com.novmah.restaurantmanagement.dto.TableDto;
import com.novmah.restaurantmanagement.entity.status.TableStatus;
import com.novmah.restaurantmanagement.service.TableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public List<TableDto> getAllTable() {
        return tableService.getAllTable();
    }

    @GetMapping("/status/{tableStatus}")
    public List<TableDto> getTablesByStatus(@PathVariable TableStatus tableStatus) {
        return tableService.getTablesByStatus(tableStatus);
    }

    @PostMapping
    public TableDto addTable(@RequestBody TableDto tableDto) {
        return tableService.addTable(tableDto);
    }

    @PatchMapping("/{id}/status/{status}")
    public TableDto updateTableStatus(@PathVariable Integer id, @PathVariable TableStatus status) {
        return tableService.updateTableStatus(id, status);
    }


    @DeleteMapping("/{id}")
    public String deleteTableById(@PathVariable Integer id) {
        return tableService.deleteTableById(id);
    }

}
