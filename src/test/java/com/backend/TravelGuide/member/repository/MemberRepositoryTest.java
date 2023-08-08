package com.backend.TravelGuide.member.repository;

import com.backend.TravelGuide.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("existsByEmail 쿼리메소드 테스트")
    void testExistsByEmail() {
        // given
        Member member = Member.builder()
                .email("user@email.com")
                .build();

        entityManager.persist(member);
        entityManager.flush();

        // when
        boolean result = memberRepository.existsByEmail("user@email.com");

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("existsByNickname 쿼리메소드 테스트")
    void testExistsByNickname() {
        // given
        Member member = Member.builder()
                .email("user@email.com")
                .nickname("user")
                .build();

        entityManager.persist(member);
        entityManager.flush();

        // when
        boolean result = memberRepository.existsByNickname("user");

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("findByEmailAndName 쿼리메소드 테스트")
    void testFindByEmailAndName() {
        // given
        Member member = Member.builder()
                .email("user@email.com")
                .name("user")
                .build();

        entityManager.persist(member);
        entityManager.flush();

        // when
        Optional<Member> optionalMember = memberRepository.findByEmailAndName("user@email.com", "user");

        // then
        assertTrue(optionalMember.isPresent());
    }

    @Test
    @DisplayName("findByEmail 쿼리메소드 테스트")
    void testFindByEmail() {
        // given
        Member member = Member.builder()
                .email("user@email.com")
                .build();

        entityManager.persist(member);
        entityManager.flush();

        // when
        Optional<Member> optionalMember = memberRepository.findByEmail("user@email.com");

        // then
        assertTrue(optionalMember.isPresent());
    }

    @Test
    @DisplayName("findByEmailAndFromSocial 쿼리메소드 테스트")
    void testFindByEmailAndFromSocial() {
        // given
        Member member = Member.builder()
                .email("user@email.com")
                .fromSocial(false)
                .build();

        entityManager.persist(member);
        entityManager.flush();

        // when
        Optional<Member> optionalMember =
                memberRepository.findByEmailAndFromSocial("user@email.com", false);

        // then
        assertTrue(optionalMember.isPresent());
    }
}