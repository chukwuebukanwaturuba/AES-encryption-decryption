package com.example.encryption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface AesRepo extends JpaRepository<EncryptEntity, Long> {
    EncryptEntity findByEmail(String email);
}
