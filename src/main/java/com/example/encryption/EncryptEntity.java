package com.example.encryption;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_users")
public class EncryptEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    private SecretKey secretKey;
   // private IvParameterSpec ivParameterSpec;
    @Lob
    private byte[]  ivParameterSpec;
    }

