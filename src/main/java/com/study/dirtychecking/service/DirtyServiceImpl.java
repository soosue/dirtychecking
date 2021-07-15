package com.study.dirtychecking.service;

import com.study.dirtychecking.entity.Dirty;
import com.study.dirtychecking.entity.DirtyRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DirtyServiceImpl implements DirtyService{
    private final DirtyRepository dirtyRepository;

    public DirtyServiceImpl(DirtyRepository dirtyRepository) {
        this.dirtyRepository = dirtyRepository;
    }

    @Override
    public String updateStatus(long id) {
        Dirty dirty = dirtyRepository.findById(id).get();
        dirty.updateStatus();

        dirtyRepository.flush();
        Dirty dirty1 = dirtyRepository.findById(id).get();
        return dirty1.getStatus();
    }
    @Override
    public List<Dirty> updateStatusAll() {
        List<Dirty> all = dirtyRepository.findAll();
        for (Dirty dirty : all) {
            dirty.updateStatus();
        }

        dirtyRepository.flush();
        return dirtyRepository.findAll();
    }
    @Override
    public String insert() {
        return dirtyRepository.save(new Dirty()).getStatus();
    }
}
