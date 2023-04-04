package my.project.wenews.member.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import my.project.wenews.entity.BaseTimeEntity;
import my.project.wenews.member.role.Role;

import javax.persistence.*;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 100)
    private String memberName;

    @Column(length = 100)
    private String memberEmail;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Role role;

    @Column(length = 500)
    private String memberPicture;

    public Member(Long memberId) {
        this.memberId = memberId;
    }

    public Member(Member member) {

        this.memberId = member.memberId;
        this.memberEmail = member.memberEmail;
    }


    public Member update(String memberEmail, String memberPicture, String memberName) {

        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPicture = memberPicture;
        return this;
    }

    public static Member of(Long memberId) {

        return new Member(memberId);
    }

}
