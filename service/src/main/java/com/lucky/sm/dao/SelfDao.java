package com.lucky.sm.dao;

import com.lucky.sm.entity.Staff;
import org.springframework.stereotype.Repository;

@Repository("selfDao")
public interface  SelfDao {
    Staff selectByAccount(String account);
}
