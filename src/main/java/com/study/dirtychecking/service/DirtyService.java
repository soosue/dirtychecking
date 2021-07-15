package com.study.dirtychecking.service;

import com.study.dirtychecking.entity.Dirty;

import java.util.List;

public interface DirtyService {
    String updateStatus(long id);
    List<Dirty> updateStatusAll();
    String insert();
}
