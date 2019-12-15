package com.crud_simple.crud.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.Valid;

import com.crud_simple.crud.model.Pengajuan;
import com.crud_simple.crud.model.Ruangan;
import com.crud_simple.crud.model.User;
import com.crud_simple.crud.repo.PengajuanRepo;
import com.crud_simple.crud.repo.RuanganRepo;
import com.crud_simple.crud.repo.UserRepo;
import com.crud_simple.crud.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private static final String adminLayout = "admin_layout";
    private static final String menu = "menu";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PengajuanRepo pengajuanRepo;

    @Autowired
    private RuanganRepo ruanganRepo;

    // Dashboard menu
    @GetMapping(value = "/dashboard")
    public ModelAndView showAdminMenu() {
        List<Pengajuan> pengajuans = pengajuanRepo.findAll();
        ModelAndView mView = new ModelAndView();
        mView.addObject("userCount", userRepo.count());
        mView.addObject("pengajuanCount", pengajuanRepo.count());
        mView.addObject("ruanganCount", ruanganRepo.count());
        mView.addObject("pengajuans", pengajuans);
        mView.addObject(menu, "dashboard");
        mView.setViewName(adminLayout);
        return mView;
    }

    /**
     * Menu User
     * 
     * @return
     */
    @GetMapping(value = "/user")
    public ModelAndView showUserMenu() {
        ModelAndView mView = new ModelAndView();
        List<User> listUser = userRepo.findAll();
        mView.addObject(menu, "user");
        mView.addObject("users", listUser);
        mView.setViewName(adminLayout);
        return mView;
    }

    @GetMapping(value = "/user/add")
    public ModelAndView showAddUserMenu() {
        ModelAndView mView = new ModelAndView();
        User user = new User();
        mView.addObject(menu, "user-add");
        mView.addObject("user", user);
        mView.setViewName(adminLayout);
        return mView;
    }

    @RequestMapping(value = "/user/edit/{id}")
    public ModelAndView showEditUserMenu(@PathVariable(name = "id") int id) {
        ModelAndView mView = new ModelAndView();
        User user = userRepo.findById(id);
        mView.addObject("user", user);
        mView.addObject(menu, "user-edit");
        mView.setViewName(adminLayout);
        return mView;
    }

    @PostMapping(value = "/user/update/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid User user, BindingResult bindingResult)
            throws NoSuchAlgorithmException {
        User dbUser = userRepo.findById(id);
        if (user.getPassword().isEmpty()) {
            user.setPassword(dbUser.getPassword());
        } else {
            user.hashPassword();
        }
        userRepo.save(user);
        return "redirect:/admin/user";
    }

    @PostMapping(value = "/user/add")
    public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult)
            throws NoSuchAlgorithmException {
        user.hashPassword();
        userRepo.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping(value = "/user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userRepo.findById(id);
        userRepo.delete(user);
        return "redirect:/admin/user";
    }
    // End menu user

    /**
     * Menu Ruangan
     * 
     * @return
     */
    @GetMapping(value = "/ruangan")
    public ModelAndView showRuanganMenu() {
        ModelAndView mView = new ModelAndView();
        List<Ruangan> ruangans = ruanganRepo.findAll();
        mView.addObject(menu, "ruangan");
        mView.addObject("ruangans", ruangans);
        mView.setViewName(adminLayout);
        return mView;
    }

    @GetMapping(value = "/ruangan/add")
    public ModelAndView showAddRuanganMenu() {
        ModelAndView mView = new ModelAndView();
        Ruangan ruangan = new Ruangan();
        mView.addObject(menu, "ruangan-add");
        mView.addObject("ruangan", ruangan);
        mView.setViewName(adminLayout);
        return mView;
    }

    @PostMapping(value = "/ruangan/add")
    public String addRuangan(@ModelAttribute("ruangan") Ruangan ruangan,
            @RequestPart(name = "fileUploaded") MultipartFile multipartFile, BindingResult bindingResult)
            throws IOException {

        String newName = FileService.uploadImage(multipartFile);
        ruangan.setGambar(newName);
        ruangan.setStatus("Tidak terpakai");
        ruanganRepo.save(ruangan);
        return "redirect:/admin/ruangan";
    }

    @RequestMapping(value = "/ruangan/edit/{id}")
    public ModelAndView showEditRuanganMenu(@PathVariable(name = "id") int id) {
        ModelAndView mView = new ModelAndView();
        Ruangan ruangan = ruanganRepo.findById(id);
        mView.addObject("ruangan", ruangan);
        mView.addObject(menu, "ruangan-edit");
        mView.setViewName(adminLayout);
        return mView;
    }

    @PostMapping(value = "/ruangan/update/{id}")
    public String updateRuangan(@PathVariable("id") int id, @Valid Ruangan ruangan, BindingResult bindingResult,
            @RequestPart(name = "fileUploaded") MultipartFile multipartFile) throws IOException {
        Ruangan dbRuangan = ruanganRepo.findById(id);
        if (multipartFile.isEmpty()) {
            ruangan.setGambar(dbRuangan.getGambar());
        } else {
            String newName = FileService.uploadImage(multipartFile);
            ruangan.setGambar(newName);
        }
        ruangan.setStatus(dbRuangan.getStatus());
        ruanganRepo.save(ruangan);
        return "redirect:/admin/ruangan";
    }

    @GetMapping(value = "/ruangan/delete/{id}")
    public String deleteRuangan(@PathVariable("id") int id) {
        Ruangan ruangan = ruanganRepo.findById(id);
        ruanganRepo.delete(ruangan);
        return "redirect:/admin/ruangan";
    }
    // End menu ruangan

    /**
     * Menu Pengajuan
     * 
     * @return
     */
    @GetMapping(value = "/pengajuan")
    public ModelAndView showPengajuanMenu() {
        ModelAndView mView = new ModelAndView();
        List<Pengajuan> pengajuans = pengajuanRepo.findAll();
        mView.addObject("pengajuans", pengajuans);
        mView.addObject(menu, "pengajuan");
        mView.setViewName(adminLayout);
        return mView;
    }

    @RequestMapping(value = "/pengajuan/{id}", method = RequestMethod.GET)
    public ModelAndView showPengajuan(@PathVariable("id") int id) {
        ModelAndView mView = new ModelAndView();
        Pengajuan pengajuan = pengajuanRepo.findById(id);
        mView.addObject("pengajuan", pengajuan);
        mView.addObject(menu, "pengajuan-view");
        mView.setViewName(adminLayout);
        return mView;
    }

    @RequestMapping(value = "/pengajuan/{id}/{status}")
    public String setStatusPengajuan(@PathVariable("id") int id, @PathVariable("status") String status) {
        Pengajuan pengajuan = pengajuanRepo.findById(id);
        pengajuan.setStatus(status);
        pengajuanRepo.save(pengajuan);
        return "redirect:/admin/pengajuan";
    }

    @GetMapping(value = "/pengajuan/delete/{id}")
    public String deletePengajuan(@PathVariable("id") int id) {
        Pengajuan pengajuan = pengajuanRepo.findById(id);
        pengajuanRepo.delete(pengajuan);
        return "redirect:/admin/pengajuan";
    }
    // End menu pengajuan
}