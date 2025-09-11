package com.example.authservice.interfaces.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.authservice.application.user.ListUsersHandler;
import com.example.authservice.interfaces.rest.dto.user.UserResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HelloController {
    private final ListUsersHandler listUsersHandler;

    @GetMapping("/hello")
    public ModelAndView list(Pageable pageable) {
        Page<UserResponse> page = listUsersHandler.handle(pageable);

        ModelAndView mv = new ModelAndView("users"); // users.html
        mv.addObject("users", page.getContent());
        mv.addObject("currentPage", page.getNumber());
        mv.addObject("totalPages", page.getTotalPages());

        return mv;
    }

    // @GetMapping("/hello")
    // public String hello() {
    //     return "Hello World";
    // }
}
