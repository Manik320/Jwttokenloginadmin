package com.userstory.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.userstory.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
