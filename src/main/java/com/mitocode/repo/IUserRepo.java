package com.mitocode.repo;

import com.mitocode.model.User;

public interface IUserRepo extends IGenericRepo<User, Integer> {
    //Este metodo me sirve para usarlo en JwtUserDetailService
    User findOneByUserName(String username);
}
