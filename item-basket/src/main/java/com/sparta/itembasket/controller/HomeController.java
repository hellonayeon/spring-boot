package com.sparta.itembasket.controller;

import com.sparta.itembasket.domain.Folder;
import com.sparta.itembasket.service.FolderService;
import com.sparta.itembasket.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final FolderService folderService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Folder> folders = folderService.getFolders(userDetails.getUser());
        model.addAttribute("folders", folders);
        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("admin", true);
        return "index";
    }
}
