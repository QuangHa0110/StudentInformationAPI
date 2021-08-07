package com.manageuniversity.controller;

import java.util.List;

import com.manageuniversity.entity.Permission;
import com.manageuniversity.service.PermissionService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {

    private PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("")
    public List<Permission> findAll(
            @RequestParam(name = "page_number", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return permissionService.findAll(pageable).toList();

    }

    @PostMapping("")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        permission.setId(null);
        return permissionService.createPermission(permission);
    }
    

}
