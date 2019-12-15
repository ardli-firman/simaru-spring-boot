package com.crud_simple.crud.repo;

import com.crud_simple.crud.model.Mahasiswa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MahasiswaRepo
 */
@Repository
public interface MahasiswaRepo extends JpaRepository<Mahasiswa, String> {

}