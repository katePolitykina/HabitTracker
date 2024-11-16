package com.example.mailservice;
import com.example.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/habit")
public class controller {

    private final MailService mailService;
    @GetMapping
    public String sendMail (){
        mailService.send("eka.politykina@gmail.com","lalala", "hi");
        return "sended";
    }


}