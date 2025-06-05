package org.example.royaleanalytics.controller;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.request.RegistrationFormDTO;
import org.example.royaleanalytics.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //TODO написать метод для возврата странички (front)
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public String getPage(Model model) {
//        return "registration";
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody RegistrationFormDTO dto) {
        Map<String, String> errors = new HashMap<>();
        authService.register(dto);
        return "redirect:/login";
    }
}
