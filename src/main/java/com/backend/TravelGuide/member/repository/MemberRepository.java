package com.backend.TravelGuide.member.repository;

import com.backend.TravelGuide.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndName(String email, String name);
    Optional<Member> findByEmailAndFromSocial(String email, boolean fromSocial);
}
