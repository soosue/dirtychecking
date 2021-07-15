package com.study.dirtychecking.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirtyRepository extends JpaRepository<Dirty, Long> {
}
