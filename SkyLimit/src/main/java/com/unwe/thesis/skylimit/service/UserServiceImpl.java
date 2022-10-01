package com.unwe.thesis.skylimit.service;

import com.unwe.thesis.skylimit.model.entity.UserEntity;
import com.unwe.thesis.skylimit.model.entity.UserRoleEntity;
import com.unwe.thesis.skylimit.model.entity.enums.RoleEnum;
import com.unwe.thesis.skylimit.model.service.UserRegisterServiceModel;
import com.unwe.thesis.skylimit.model.view.UserViewModel;
import com.unwe.thesis.skylimit.repository.UserRepository;
import com.unwe.thesis.skylimit.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class UserServiceImpl {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserViewModel findByUsername(String username){
        //TODO: Finish this

        return null;
    }


    public boolean isUsernameOccupied(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public boolean isEmailOccupied(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public void registerUser(UserRegisterServiceModel userRegisterServiceModel) {
        UserEntity currentUser = this.modelMapper.map(userRegisterServiceModel, UserEntity.class);
        currentUser.setRoles(Set.of(this.userRoleRepository.findByRole(RoleEnum.USER)));
        currentUser.setOrders(new LinkedHashSet<>());
        currentUser.setPassword(this.passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        this.userRepository.save(currentUser);
    }


    public void seedUsers() {

        if (this.userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setFirstName("Admin");
            admin.setLastName("Adminov");
            admin.setAddress("Admin street 16");
            admin.setEmail("admin@abv.bg");
            admin.setOrders(new HashSet<>());
            admin.setPassword(this.passwordEncoder.encode("12345"));

            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setRole(RoleEnum.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRole(RoleEnum.USER);
            this.userRoleRepository.saveAll(Set.of(adminRole, userRole));

            admin.setRoles(Set.of(adminRole, userRole));

            UserEntity user = new UserEntity();
            user.setUsername("user");
            user.setFirstName("User");
            user.setLastName("Userov");
            user.setAddress("Userov street 17");
            user.setEmail("user@abv.bg");
            user.setOrders(new HashSet<>());
            user.setPassword(this.passwordEncoder.encode("12345"));
            user.setRoles(Set.of(userRole));

            this.userRepository.saveAll(Set.of(admin, user));
        }
    }

}
