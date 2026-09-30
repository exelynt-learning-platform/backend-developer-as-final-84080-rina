package com.example.booking.exception;

import jakarta.servlet.http.HttpServletRequest; import jakarta.validation.ConstraintViolationException; import org.springframework.http.*; import org.springframework.security.access.AccessDeniedException; import org.springframework.security.core.AuthenticationException; import org.springframework.web.HttpMediaTypeNotSupportedException; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import org.springframework.http.converter.HttpMessageNotReadableException; import java.time.Instant; import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(NotFoundException.class) ResponseEntity<ApiError> notFound(NotFoundException e,HttpServletRequest r){return build(HttpStatus.NOT_FOUND,e.getMessage(),r,Map.of());}
 @ExceptionHandler(BadRequestException.class) ResponseEntity<ApiError> bad(BadRequestException e,HttpServletRequest r){return build(HttpStatus.BAD_REQUEST,e.getMessage(),r,Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> m=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->m.put(x.getField(),x.getDefaultMessage()));return build(HttpStatus.BAD_REQUEST,"Validation failed",r,m);}
 @ExceptionHandler({ConstraintViolationException.class,HttpMessageNotReadableException.class,IllegalArgumentException.class,HttpMediaTypeNotSupportedException.class}) ResponseEntity<ApiError> malformed(Exception e,HttpServletRequest r){return build(HttpStatus.BAD_REQUEST,"Invalid request",r,Map.of());}
 @ExceptionHandler(AuthenticationException.class) ResponseEntity<ApiError> authentication(AuthenticationException e,HttpServletRequest r){return build(HttpStatus.UNAUTHORIZED,"Invalid username or password",r,Map.of());}
 @ExceptionHandler(AccessDeniedException.class) ResponseEntity<ApiError> denied(AccessDeniedException e,HttpServletRequest r){return build(HttpStatus.FORBIDDEN,"Access denied",r,Map.of());}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiError> other(Exception e,HttpServletRequest r){return build(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected server error",r,Map.of());}
 private ResponseEntity<ApiError> build(HttpStatus s,String msg,HttpServletRequest r,Map<String,String> v){return ResponseEntity.status(s).body(new ApiError(Instant.now(),s.value(),s.getReasonPhrase(),msg,r.getRequestURI(),v));}
}
