package com.manageuniversity.service;

import java.util.List;

import com.manageuniversity.entity.Role;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.repository.RoleRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Cacheable(cacheNames="roleAll")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public ResponseEntity<Role> createRole(Role role){
        if(role== null){
            throw new BadRequestException("Role is null");
        }else{
            return ResponseEntity.ok().body(roleRepository.save(role));
        }
    }

    @CacheEvict(cacheNames = "role", key = "#id")
    public ResponseEntity<String> deleteRole(Integer id){
        if(id == null){
            throw new BadRequestException("id is null");
        }else {
            roleRepository.deleteById(id);
            return ResponseEntity.ok().body("Deleted role successful");
        }
    }

}
