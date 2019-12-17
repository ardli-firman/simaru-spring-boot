package com.crud_simple.crud.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.crud_simple.crud.model.User;
import com.crud_simple.crud.repo.MahasiswaRepo;
import com.crud_simple.crud.repo.PengajuanRepo;
import com.crud_simple.crud.repo.RuanganRepo;
import com.crud_simple.crud.repo.UserRepo;
import com.crud_simple.crud.services.UtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AppController
 */
@Controller
public class AppController {
    @Autowired
    private MahasiswaRepo mhsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RuanganRepo ruanganRepo;

    @Autowired
    private PengajuanRepo pengajuanRepo;

    @GetMapping("/")
    public String showHomeMenu(Model model) {
        model.addAttribute("menu", "home");
        model.addAttribute("pengajuans", pengajuanRepo.findAll());
        return "home_layout";
    }

    @GetMapping("/login")
    public String showLoginMenu(Model model) {
        model.addAttribute("menu", "login");
        model.addAttribute("aksi", "/do-login");
        return "home_layout";

    }

    @GetMapping("/logout")
    public String doLogout(HttpSession httpSession) {
        httpSession.removeAttribute("user_login");
        return "redirect:/";
    }

    // Login function
    @RequestMapping("/do-login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password,
            HttpSession httpSession) throws NoSuchAlgorithmException {
        List<User> user = userRepo.findUserByUsername(username);
        String passwd = UtilService.getMD5(password);
        if (user.size() < 0) {
            return "redirect:/login";
        }
        if (passwd.equals(user.get(0).getPassword())) {
            if (user.get(0).getRole().equals("user")) {
                httpSession.setAttribute("user_login", user.get(0));
                return "redirect:user/dashboard";
            } else if (user.get(0).getRole().equals("admin")) {
                httpSession.setAttribute("user_login", user.get(0));
                return "redirect:admin/dashboard";
            }
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/test")
    public String test(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user_login");
        System.out.print(user.getId());
        return "home";
    }

    // Tested
    @RequestMapping("/daftar-mahasiswa")
    public String showDaftarMahasiswa(Model model) {
        model.addAttribute("daftarMahasiswa", mhsRepo.findAll());
        return "daftar_mahasiswa";
    }

    @RequestMapping("/daftar-user")
    public String showDaftarUser(Model model) {
        model.addAttribute("daftarUser", userRepo.findAll());
        return "daftar_user";
    }

    @RequestMapping("/daftar-ruangan")
    public String showDaftarRuangan(Model model) {
        model.addAttribute("daftarRuangan", ruanganRepo.findAll());
        return "daftar_ruangan";
    }
}