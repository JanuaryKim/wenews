package my.project.wenews.member.entity;

import lombok.*;
import my.project.wenews.entity.BaseTimeEntity;
import my.project.wenews.member.role.Role;

import javax.persistence.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 10)
    private String memberNickname;

    @Column(length = 12)
    private String memberPassword;

    @Column(length = 100)
    private String memberEmail;

    @Column
    private Byte memberAge;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Role role;

    public Member(Long memberId) {
        this.memberId = memberId;
    }

    public static Member newInstance(Member member) {
        return new Member(member);
    }

    public Member(Member member) {

        this.memberId = member.memberId;
        this.memberNickname = member.memberNickname;
        this.memberPassword = member.memberPassword;
        this.memberEmail = member.memberEmail;
        this.memberAge = member.memberAge;
    }

}
