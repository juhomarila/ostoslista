package fi.ruoka.ostoslista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ruoka.ostoslista.dto.UserDto;
import fi.ruoka.ostoslista.logging.OstosListaLogger;
import fi.ruoka.ostoslista.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    private final OstosListaLogger logger;

    public UserController(OstosListaLogger logger) {
        this.logger = logger;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto dto) {
        logger.postLogStart("login");
        var vsr = userService.login(dto);
        logger.postLogEnd("login");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.INTERNAL_SERVER_ERROR
                                : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        logger.postLogStart("refreshToken");
        String refreshToken = request.getHeader("Authorization");
        var vsr = userService.refreshToken(refreshToken);
        logger.postLogEnd("refreshToken");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.INTERNAL_SERVER_ERROR
                                : HttpStatus.BAD_REQUEST);
    }
}
