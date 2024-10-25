package com.mamunetond;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/status")
    public String getStatus() {
        return "Service is running";
    }
}
