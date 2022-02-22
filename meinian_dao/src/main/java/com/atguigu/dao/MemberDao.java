package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {

    Member findByPhone(String telephone);

    void addMember(Member member);

    Member findById(Integer memberId);
}
