package com.crud_simple.crud.repo;

import com.crud_simple.crud.model.Ruangan;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RuanganRepo
 */
public interface RuanganRepo extends JpaRepository<Ruangan, Integer> {
    Ruangan findById(int id);
}