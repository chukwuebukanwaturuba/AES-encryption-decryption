package com.example.encryption;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AesController {
    private final AesService aesService;

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody EncryptionRequest request) throws Exception {
        return aesService.encrypt(request);
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody EncryptionRequest request) throws Exception {
        return aesService.decrypt(request);
    }

}
