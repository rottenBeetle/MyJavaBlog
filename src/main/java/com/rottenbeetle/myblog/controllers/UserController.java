package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Role;
import com.rottenbeetle.myblog.domain.User;
import com.rottenbeetle.myblog.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public String listUsers(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "user-list";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("roles", Role.values());
        model.addAttribute("user",user);
        return "user-edit";
    }

    @GetMapping("delete/{user}")
    public String deleteUser(@PathVariable User user){
        userRepository.delete(user);
        return "redirect:/user/list";
    }

    @GetMapping("block/{user}")
    public String blockUser(@PathVariable User user){

        user.setActive(!user.isActive());

        userRepository.save(user);
        return "redirect:/user/list";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String,String> form,
            @RequestParam("id") User user){

        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
        return "redirect:/user/list";
    }
}
