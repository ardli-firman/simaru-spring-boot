package com.crud_simple.crud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Ruangan
 */
@Entity
@Table(name = "ruangan")
@Getter
@Setter
public class Ruangan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String nama_ruangan;

    public String deskripsi;

    public String gambar;

    public String status;
}