package com.example.bids.controller;

import com.example.bids.entity.UserDto;
import com.example.bids.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/sign")
@Controller
public class SignController {
    private final SignService signService;

    @GetMapping("/up")
    public String get_signUp() {
        return "pages/sign/up";
    }

    @PostMapping("/up")
    public String post_signUp(@RequestParam Map<String, String> userData) {
        signService.signUp(userData);
        return "pages/sign/in";
    }

    @PostMapping("/emailCheck")
    public ResponseEntity emailCheck(@RequestParam Map<String, String> userData) {
        if(signService.emailCheck(userData)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);
    }

    @GetMapping("/in")
    public String get_signIn(Model model, HttpSession session) {
        return "pages/sign/in";
    }

    @ResponseBody
    @PostMapping("/in")
    public ResponseEntity post_signIn(@RequestParam Map<String, String> userData, HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto userDto = signService.signIn(userData);
        if(userDto != null) {
            session.setAttribute("user", userDto);
            return ResponseEntity.ok().body("true");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("false");
    }

    @ResponseBody
    @PostMapping("/out")
    public void post_signOut(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();
    }
}
