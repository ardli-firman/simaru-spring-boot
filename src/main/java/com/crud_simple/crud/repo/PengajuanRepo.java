package com.crud_simple.crud.repo;

import java.util.List;

import com.crud_simple.crud.model.Pengajuan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PengajuanRepo extends JpaRepository<Pengajuan, Integer> {
    Pengajuan findById(int id);

    List<Pengajuan> findAllByUserId(int id);
}