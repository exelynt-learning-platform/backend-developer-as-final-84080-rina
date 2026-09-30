package com.example.booking.security;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class JwtServiceTest{
 @Test void tokenContainsUsernameAndIsValid(){JwtService s=new JwtService("01234567890123456789012345678901",3600000);String t=s.generateToken("alice","USER");assertTrue(s.isValid(t));assertEquals("alice",s.extractUsername(t));}
 @Test void shortSecretIsRejected(){assertThrows(IllegalArgumentException.class,()->new JwtService("short",1000));}
}
