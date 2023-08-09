package com.backend.TravelGuide.member.domain;

import lombok.*;

public class MemberResponseDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MemberInformationDTO {
        private String email;
        private String name;
        private String nickname;
    }
}
