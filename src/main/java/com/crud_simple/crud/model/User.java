package com.crud_simple.crud.model;

import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.crud_simple.crud.services.UtilService;

import lombok.Getter;
import lombok.Setter;

/**
 * User
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String nama;

    public String email;

    public String no_telp;

    public String jk;

    public String username;

    public String password;

    public String role;

    public void hashPassword() throws NoSuchAlgorithmException {
        this.password = UtilService.getMD5(this.password);
    }
}