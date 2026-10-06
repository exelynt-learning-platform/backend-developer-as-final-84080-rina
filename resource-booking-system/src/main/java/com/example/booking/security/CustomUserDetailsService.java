package com.example.booking.security;
import com.example.booking.entity.AppUser;
import com.example.booking.repository.UserRepository; 
import org.springframework.security.core.userdetails.*; 
import org.springframework.stereotype.Service;
@Service
 public class CustomUserDetailsService implements UserDetailsService{
 private final UserRepository repo; 
  public CustomUserDetailsService(UserRepository r){repo=r;}
 @Override 
  public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
   AppUser u=repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
   return User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole().name()).disabled(!u.isEnabled()).build();
  }
}
