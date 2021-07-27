package com.manageuniversity.controller;

import java.util.List;

import com.manageuniversity.entity.Role;
import com.manageuniversity.service.RoleService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return roleService.createRole(role);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<String> deleteRole(@PathVariable(name = "id") Integer id){
        return roleService.deleteRole(id);
    }

}
