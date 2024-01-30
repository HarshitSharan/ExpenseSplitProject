package org.hsharan.repository;

import org.hsharan.model.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository extends CrudRepository<UserDetails,String> {

}
