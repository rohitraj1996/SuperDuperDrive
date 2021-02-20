package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/save")
    public String saveCredential(Credential credential, Authentication authentication, Model model){
        String username = authentication.getName();

        if(credential.getCredentialId() == null){
            this.credentialService.createCredential(username, credential);
        } else {
            this.credentialService.updateCredential(credential);
        }
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model){
        this.credentialService.deleteCredential(credentialId);
        model.addAttribute("result", "success");
        return "result";
    }
}
