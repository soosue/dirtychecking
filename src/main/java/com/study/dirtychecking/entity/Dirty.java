package com.study.dirtychecking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dirty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String status = "N";

    public void updateStatus() {
        status = "Y";
    }

    public String getStatus() {
        return status;
    }
}
