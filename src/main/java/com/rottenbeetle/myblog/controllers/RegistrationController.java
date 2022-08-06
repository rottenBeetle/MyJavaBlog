package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Role;
import com.rottenbeetle.myblog.domain.User;
import com.rottenbeetle.myblog.repo.UserRepository;
import com.rottenbeetle.myblog.service.MailSender;
import com.rottenbeetle.myblog.service.UserService;
import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.UUID;

@Controller
public class RegistrationController {
    //FIXME Убрать репозиторий
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailSender mailSender;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,@RequestParam() String repeatPassword, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (!user.getPassword().equals(repeatPassword)){
            model.addAttribute("message","Пароли не совпадают!");
            return "registration";
        }

        if (userFromDb != null){
            model.addAttribute("message","Данный никнейм уже занят!");
            return "registration";
        }

        userFromDb = userRepository.findByEmail(user.getEmail());

        if (userFromDb != null){
            model.addAttribute("message","Данная почта уже занята!");
            return "registration";
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())){
            String message = "Здравствуйте " + user.getUsername() + "! Вы зарегистрировались на сайте CourseX. " +
                    "Пожалуйста перейдите по данной ссылке для подтверждения аккаунта: http://localhost:8080/activate/" + user.getActivationCode();
            mailSender.send(user.getEmail(),"Код активации",message);
        }
        model.addAttribute("user",user);
        return "confirm-account";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if (isActivated){
            model.addAttribute("message","Пользователь успешно зарегестрирован!");
        }else {
            model.addAttribute("message", "Ссылка не актуальна!");
        }

        return "login";
    }
}
