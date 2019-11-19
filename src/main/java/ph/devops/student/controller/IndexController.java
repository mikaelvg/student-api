package ph.devops.student.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    Environment environment;

    @GetMapping
    public String sayHello() throws UnknownHostException {
        return "Welcome to Mikael's Student API App! \n Your IP is: " + InetAddress.getLocalHost().getHostAddress();
    }
}
