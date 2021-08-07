// package com.manageuniversity.config.jwt;

// import java.util.Arrays;

// import com.manageuniversity.entity.Permission;
// import com.manageuniversity.repository.PermissionRepository;
// import com.manageuniversity.repository.RoleRepository;
// import com.manageuniversity.repository.UserRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationListener;
// import org.springframework.context.event.ContextRefreshedEvent;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.transaction.annotation.Transactional;

// public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

//   boolean alreadySetup = false;

//   private UserRepository userRepository;

//   private RoleRepository roleRepository;

//   private PermissionRepository permissionRepository;

//   private PasswordEncoder passwordEncoder;

//   public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository,
//       PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
//     this.userRepository = userRepository;
//     this.roleRepository = roleRepository;
//     this.permissionRepository = permissionRepository;
//     this.passwordEncoder = passwordEncoder;
//   }

//   @Override
//   public void onApplicationEvent(ContextRefreshedEvent event) {
//     if(alreadySetup){
//       return ;
//     }

//     Permission readPermission = createPermissionIfNotFound("READ_PERMISSION");
//     Permission createPermission = createPermissionIfNotFound("CREATE_PERMISSION");
//     Permission updatePermission = 
    
    
//   }

//   private Permission createPermissionIfNotFound(String name) {
//       Permission permission = permissionRepository.findByName(name);
//       if(permission == null){
//         permission = new Permission(name);
//         permissionRepository.save(permission);
//       }

//     return permission;
//   }

  

 
  

// }
