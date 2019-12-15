package com.crud_simple.crud.repo;

import com.crud_simple.crud.model.Pengajuan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PengajuanRepo extends JpaRepository<Pengajuan, Integer> {
    Pengajuan findById(int id);
}