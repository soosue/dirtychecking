package com.study.dirtychecking.controller;

import com.study.dirtychecking.entity.Dirty;
import com.study.dirtychecking.entity.DirtyRepository;
import com.study.dirtychecking.service.DirtyService;
import com.study.dirtychecking.service.DirtyServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DirtyCheckingController {

    private final DirtyService dirtyService;
    private final DirtyRepository dirtyRepository;

    public DirtyCheckingController(DirtyService dirtyService, DirtyRepository dirtyRepository) {
        this.dirtyService = dirtyService;
        this.dirtyRepository = dirtyRepository;
    }

    @GetMapping("/insert")
    public String insert() {
        return dirtyService.insert();
    }

    @GetMapping("/{id}")
    public String dirtyChecking(@PathVariable Long id) {

        return dirtyService.updateStatus(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dirty>> updateAll() {
        List<Dirty> dirties = dirtyService.updateStatusAll();
        return ResponseEntity.ok(dirties);
    }
    @GetMapping("/tx")
    public String tx() {
        dirtyRepository.findById(1l);
        dirtyRepository.findById(1l);
        dirtyRepository.findById(1l);
        return "ok";
    }

}
