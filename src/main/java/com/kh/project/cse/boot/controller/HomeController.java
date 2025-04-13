package com.kh.project.cse.boot.controller;

import com.kh.project.cse.boot.domain.vo.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(HttpSession session, Model model) {
        Member member = (Member)session.getAttribute("loginMember");
        int storeNo = member.getStoreNo();
        model.addAttribute("storeNo", storeNo);
        model.addAttribute("member", member);

        return "redirect:/loginForm";
    }


}
