package com.unwe.thesis.skylimit.repository;

import com.unwe.thesis.skylimit.model.entity.UserRoleEntity;
import com.unwe.thesis.skylimit.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByRole(RoleEnum role);
}
