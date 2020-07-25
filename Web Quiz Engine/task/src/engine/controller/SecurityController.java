package engine.controller;

import engine.controller.dto.UserRequest;
import engine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class SecurityController {

    private final UserService userService;

    @PostMapping("/api/register")
    public void registration(@Valid @RequestBody UserRequest userRequest) {
        userService.registration(userRequest.getEmail(), userRequest.getPassword());
    }
}
