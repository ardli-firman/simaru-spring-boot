package com.crud_simple.crud.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.crud_simple.crud.services.DateService;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pengajuan")
@Getter
@Setter
public class Pengajuan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;

    @OneToOne
    @JoinColumn(name = "ruangan_id")
    public Ruangan ruangan;

    public String mulai;
    public String selesai;
    public String keperluan;
    public String berkas;
    public String status;

    public String getWaktu() {
        String waktu = DateService.getWaktu(this.mulai, this.selesai);

        return waktu;
    }

    public String getDay() {
        String day = DateService.getDate(this.mulai);

        return day;
    }

    public String mulai() {
        String mulai = DateService.indoDate(this.mulai);

        return mulai;
    }

    public String selesai() {
        String selesai = DateService.indoDate(this.selesai);

        return selesai;
    }
}