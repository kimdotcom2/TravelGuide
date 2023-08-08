package com.backend.TravelGuide.member.service;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.domain.MemberDTO;
import com.backend.TravelGuide.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">> MemberDetailsService loadUserByUsername: " + username);

        Optional<Member> optionalMember = memberRepository.findByEmailAndFromSocial(username, true);

        if (!optionalMember.isPresent()) {
            optionalMember = memberRepository.findByEmailAndFromSocial(username, false);
        }

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("Check your email or pwd");
        }

        Member member = optionalMember.get();
        log.info(">> member: " + member);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        MemberDTO memberDTO = new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                authorities
        );

        memberDTO.setName(member.getName());
        memberDTO.setFromSocial(member.isFromSocial());

        return memberDTO;
    }
}
