package com.example.booking.controller;
import com.example.booking.dto.*; import com.example.booking.service.ResourceService; import jakarta.validation.Valid; import org.springframework.data.domain.*; import org.springframework.http.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/resources") public class ResourceController{
 private final ResourceService service;public ResourceController(ResourceService s){service=s;}
 @GetMapping public Page<ResourceResponse>all(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="10")int size,@RequestParam(defaultValue="id")String sortBy,@RequestParam(defaultValue="asc")String direction){return service.all(pageable(page,size,sortBy,direction));}
 @GetMapping("/{id}")public ResourceResponse get(@PathVariable Long id){return service.get(id);}
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public ResponseEntity<ResourceResponse>create(@Valid@RequestBody ResourceRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
 @PutMapping("/{id}")@PreAuthorize("hasRole('ADMIN')")public ResourceResponse update(@PathVariable Long id,@Valid@RequestBody ResourceRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}")@PreAuthorize("hasRole('ADMIN')")public ResponseEntity<Void>delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
 private Pageable pageable(int page,int size,String sort,String dir){if(page<0||size<1||size>100)throw new IllegalArgumentException("page must be >=0 and size must be 1..100");Sort s="desc".equalsIgnoreCase(dir)?Sort.by(sort).descending():Sort.by(sort).ascending();return PageRequest.of(page,size,s);}
}
