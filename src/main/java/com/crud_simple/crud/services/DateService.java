package com.crud_simple.crud.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * DateService
 */
@Service
public class DateService {

    public static String indoDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfO = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date myDate;
        String tgl;
        try {
            myDate = (Date) sdf.parse(date);
            tgl = sdfO.format(myDate);
            return tgl;
        } catch (ParseException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";
    }

    public static String getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfO = new SimpleDateFormat("EEE");
        Date myDate;
        String tgl;
        try {
            myDate = (Date) sdf.parse(date);
            tgl = sdfO.format(myDate);
            switch (tgl) {
            case "Sun":
                tgl = "Minggu";
                break;
            case "Mon":
                tgl = "Senin";
                break;
            case "Tue":
                tgl = "Selasa";
                break;
            case "Wed":
                tgl = "Rabu";
                break;
            case "Thu":
                tgl = "Kamis";
                break;
            case "Fri":
                tgl = "Jumat";
                break;
            case "Sat":
                tgl = "Sabtu";
                break;
            default:
                break;
            }
            return tgl;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}