package com.example.booking.security;
import org.junit.jupiter.api.Test; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import static org.junit.jupiter.api.Assertions.*;
class SecurityConfigTest{@Test void passwordsAreBcryptEncoded(){BCryptPasswordEncoder e=new BCryptPasswordEncoder();String h=e.encode("secret");assertNotEquals("secret",h);assertTrue(e.matches("secret",h));}}
