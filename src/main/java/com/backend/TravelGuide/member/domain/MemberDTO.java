package com.backend.TravelGuide.member.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberDTO extends User {
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    // User 상속 후 생성자 생성 필수
    // password는 User 부모클래스를 사용하므로 멤버변수로 선언하지 않는다
    public MemberDTO(String username,
                     String password,
                     boolean fromSocial,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;   // password를 넘겨주면 passwordEncoder가 자동으로 검사해줌
        this.fromSocial = fromSocial;
    }
}
