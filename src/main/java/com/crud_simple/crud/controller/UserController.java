package com.crud_simple.crud.controller;

import java.util.Date;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import com.crud_simple.crud.model.Pengajuan;
import com.crud_simple.crud.model.Ruangan;
import com.crud_simple.crud.model.User;
import com.crud_simple.crud.repo.PengajuanRepo;
import com.crud_simple.crud.repo.RuanganRepo;
import com.crud_simple.crud.repo.UserRepo;
import com.crud_simple.crud.services.DateService;
import com.crud_simple.crud.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final String userLayout = "user_layout";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PengajuanRepo pengajuanRepo;

    @Autowired
    private RuanganRepo ruanganRepo;

    /**
     * Manu dashboard
     * 
     * @return
     */
    @GetMapping(value = "/dashboard")
    public ModelAndView showDashboardMenu() {
        ModelAndView mView = new ModelAndView();
        List<Pengajuan> pengajuans = pengajuanRepo.findAll();
        mView.addObject("pengajuanCount", pengajuans.size());
        mView.addObject("pengajuans", pengajuans);
        mView.addObject("menu", "dashboard");
        mView.setViewName(userLayout);
        return mView;
    }
    // End dashboard menu

    /**
     * Menu ruangan
     * 
     * @return
     */
    @GetMapping(value = "/ruangan")
    public ModelAndView showRuanganMenu() {
        ModelAndView mView = new ModelAndView();
        List<Ruangan> ruangans = ruanganRepo.findAll();
        mView.addObject("ruangans", ruangans);
        mView.addObject("menu", "ruangan");
        mView.setViewName(userLayout);
        return mView;
    }

    @RequestMapping(value = "/ruangan/add-pengajuan/{id}")
    public ModelAndView showAddPengajuanMenu(@PathVariable("id") int id) {
        Ruangan ruangan = ruanganRepo.findById(id);
        Pengajuan pengajuan = new Pengajuan();
        ModelAndView mView = new ModelAndView();
        mView.addObject("pengajuan", pengajuan);
        mView.addObject("ruangan", ruangan);
        mView.addObject("menu", "ruangan-add-pengajuan");
        mView.setViewName(userLayout);
        return mView;
    }

    @PostMapping(value = "/ruangan/add-pengajuan/{id}/add")
    public String addPengajuan(@PathVariable("id") int id,
            @RequestPart(name = "fileUploaded", required = false) MultipartFile multipartFile,
            @RequestPart(name = "waktu") String waktu, @ModelAttribute("pengajuan") Pengajuan pengajuan)
            throws ParseException, IOException {
        Ruangan ruangan = ruanganRepo.findById(id);
        User user = userRepo.findById(5);

        String[] wkt = waktu.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date mulai = sdf.parse(wkt[0]);
        Date selesai = sdf.parse(wkt[1]);
        java.sql.Timestamp sqlMulai = new java.sql.Timestamp(mulai.getTime());
        java.sql.Timestamp sqlSelesai = new java.sql.Timestamp(selesai.getTime());
        String newName = FileService.uploadBerkas(multipartFile);

        pengajuan.setUser(user);
        pengajuan.setRuangan(ruangan);
        pengajuan.setBerkas(newName);
        pengajuan.setSelesai(sqlSelesai.toString());
        pengajuan.setMulai(sqlMulai.toString());
        pengajuan.setStatus("pending");

        pengajuanRepo.save(pengajuan);

        return "redirect:/user/ruangan";
    }
    // End ruangan menu

    /**
     * Menu pengajuan
     * 
     * @return
     */
    @GetMapping(value = "/pengajuan")
    public ModelAndView showPengajuanMenu() {
        List<Pengajuan> pengajuans = pengajuanRepo.findAll();
        ModelAndView mView = new ModelAndView();
        mView.addObject("pengajuans", pengajuans);
        mView.addObject("menu", "pengajuan");
        mView.setViewName(userLayout);
        return mView;
    }

    @GetMapping(value = "/pengajuan/edit/{id}")
    public ModelAndView showPengajuanEditMenu(@PathVariable("id") int id) {
        Pengajuan pengajuan = pengajuanRepo.findById(id);
        ModelAndView mView = new ModelAndView();
        mView.addObject("pengajuan", pengajuan);
        mView.addObject("menu", "pengajuan-edit");
        mView.setViewName(userLayout);
        return mView;
    }

    @PostMapping(value = "/pengajuan/update/{id}")
    public String updatePengajuan(@PathVariable("id") int id, @Valid Pengajuan pengajuan,
            @RequestPart(name = "fileUploaded") MultipartFile multipartFile, @RequestPart(name = "waktu") String waktu)
            throws ParseException, IOException {

        Pengajuan dbPengajuan = pengajuanRepo.findById(id);

        String[] wkt = waktu.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date mulai = sdf.parse(wkt[0]);
        Date selesai = sdf.parse(wkt[1]);
        java.sql.Timestamp sqlMulai = new java.sql.Timestamp(mulai.getTime());
        java.sql.Timestamp sqlSelesai = new java.sql.Timestamp(selesai.getTime());

        if (multipartFile.isEmpty()) {
            pengajuan.setBerkas(dbPengajuan.getBerkas());
        } else {
            String newName = FileService.uploadBerkas(multipartFile);
            pengajuan.setBerkas(newName);
        }

        pengajuan.setUser(dbPengajuan.getUser());
        pengajuan.setRuangan(dbPengajuan.getRuangan());
        pengajuan.setMulai(sqlMulai.toString());
        pengajuan.setSelesai(sqlSelesai.toString());
        pengajuan.setStatus(dbPengajuan.getStatus());

        pengajuanRepo.save(pengajuan);
        return "redirect:/user/pengajuan";
    }

    @GetMapping(value = "/pengajuan/{id}/cancel")
    public String cancelPengajuan(@PathVariable("id") int id) {
        Pengajuan pengajuan = pengajuanRepo.findById(id);
        pengajuan.setStatus("cancel");

        pengajuanRepo.save(pengajuan);
        return "redirect:/user/pengajuan";
    }
    // End pengajuan menu

}