package com.manageuniversity.service;

import com.manageuniversity.entity.Permission;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.PermissionRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Cacheable(cacheNames = "permissionAll")
    public Page<Permission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    public ResponseEntity<Permission> createPermission(Permission permission) {
        return ResponseEntity.ok().body(permissionRepository.save(permission));
    }

    public ResponseEntity<Permission> updatePermission(Integer id, Permission permission) {
        Permission permission2 = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));

        permission.setId(permission2.getId());

        return ResponseEntity.ok().body(permissionRepository.save(permission));

    }

    public ResponseEntity<String> deletePermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));

        permissionRepository.delete(permission);
        return ResponseEntity.ok().body("Permission deleted successful");
    }

}
