package com.example.booking.service;
import com.example.booking.dto.*;
import com.example.booking.repository.UserRepository; 
import com.example.booking.security.JwtService; 
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;
@Service 
 public class AuthService{
 private final AuthenticationManager auth; 
  private final JwtService jwt;
  private final UserRepository users;
 public AuthService(AuthenticationManager a,JwtService j,UserRepository u){auth=a;jwt=j;users=u;}
 public LoginResponse login(LoginRequest r)
  {
   auth.authenticate(new UsernamePasswordAuthenticationToken(r.username(),r.password()));
   var u=users.findByUsername(r.username()).orElseThrow();
   return new LoginResponse(jwt.generateToken(u.getUsername(),u.getRole().name()),"Bearer",jwt.getExpirationMs(),u.getUsername(),u.getRole().name());}
}
