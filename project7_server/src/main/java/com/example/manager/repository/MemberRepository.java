package com.example.manager.repository;

import com.example.manager.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Using JPA
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    /**
     * ID, Password 에 일치하는 회원 조회
     * @param id 조회 대상 ID
     * @param password 조회 대상 Password
     * @return 회원 조회 결과
     */
    Optional<Member> findByIdAndPassword(String id, String password);

    /**
     * 이름으로 회원 조회
     * @param name 조회할 이름
     * @return 조회 결과(동명이인이 있을 수 있기에 List)
     */
    List<Member> findByName(String name);
    
    Optional<Member> findById(String id);
}
