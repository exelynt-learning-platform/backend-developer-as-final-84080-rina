package com.example.booking.service;
import com.example.booking.dto.*; import com.example.booking.entity.Resource; import com.example.booking.exception.NotFoundException; import com.example.booking.repository.ResourceRepository; import org.springframework.data.domain.*; import org.springframework.stereotype.Service;
@Service public class ResourceService{
 private final ResourceRepository repo; public ResourceService(ResourceRepository r){repo=r;}
 public Page<ResourceResponse> all(Pageable p){return repo.findAll(p).map(this::toDto);}
 public ResourceResponse get(Long id){return toDto(find(id));}
 public ResourceResponse create(ResourceRequest r){Resource x=new Resource();apply(x,r);return toDto(repo.save(x));}
 public ResourceResponse update(Long id,ResourceRequest r){Resource x=find(id);apply(x,r);return toDto(repo.save(x));}
 public void delete(Long id){if(!repo.existsById(id))throw new NotFoundException("Resource not found");repo.deleteById(id);}
 public Resource find(Long id){return repo.findById(id).orElseThrow(()->new NotFoundException("Resource not found: "+id));}
 private void apply(Resource x,ResourceRequest r){x.setName(r.name());x.setDescription(r.description());x.setPrice(r.price());x.setAvailable(r.available());}
 private ResourceResponse toDto(Resource x){return new ResourceResponse(x.getId(),x.getName(),x.getDescription(),x.getPrice(),x.isAvailable());}
}
