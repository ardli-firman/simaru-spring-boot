package com.crud_simple.crud.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * Mahasiswa
 */
@Entity
public class Mahasiswa {

    @Id
    @Getter @Setter
    public String nim;

    @Getter @Setter
    public String nama;

    @Getter @Setter
    public String prodi;

}