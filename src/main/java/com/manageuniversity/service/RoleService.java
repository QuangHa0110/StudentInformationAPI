package com.manageuniversity.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.manageuniversity.entity.Permission;
import com.manageuniversity.entity.Role;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.PermissionRepository;
import com.manageuniversity.repository.RoleRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    private PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Cacheable(cacheNames = "roleAll")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public ResponseEntity<Role> createRole(Role role) {
        if (role == null) {
            throw new BadRequestException("Role is null");
        } else {
            return ResponseEntity.ok().body(roleRepository.save(role));
        }
    }

    @CacheEvict(cacheNames = "role", key = "#id")
    public ResponseEntity<String> deleteRole(Integer id) {
        if (id == null) {
            throw new BadRequestException("id is null");
        } else {
            roleRepository.deleteById(id);
            return ResponseEntity.ok().body("Deleted role successful");
        }
    }

    public ResponseEntity<String> addPermission(Integer role_id, Integer permission_id) {
        Role role = roleRepository.findById(role_id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + role_id));
        Permission permission = permissionRepository.findById(permission_id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + permission_id));

        if (role.getPermissions().isEmpty()) {
            Collection<Permission> listPermissions = new ArrayList<>();
            listPermissions.add(permission);
            role.setPermissions(listPermissions);
        } else {
            role.getPermissions().add(permission);
        }

        if (permission.getRoles().isEmpty()) {
            Collection<Role> listRoles = new ArrayList<>();
            listRoles.add(role);
            permission.setRoles(listRoles);
        } else {
            permission.getRoles().add(role);
        }

        roleRepository.save(role);

        return ResponseEntity.ok().body("Add permission successful");
    }

    public ResponseEntity<String> addListPermission(Integer role_id, List<Permission> list){
        for(Permission permission: list){
            addPermission(role_id, permission.getId());
        }

        return ResponseEntity.ok().body("Add list permission successful");
    }

   

}
