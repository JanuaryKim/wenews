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
    @Column(unique = true, length = 30)
    private String memberId;

    @Column(length = 100)
    private String memberEmail;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Role role;

    @Column(length = 500)
    private String memberPicture;

    public Member(String memberId) {
        this.memberId = memberId;
    }

    public static Member newInstance(Member member) {
        return new Member(member);
    }

    public Member(Member member) {

        this.memberId = member.memberId;
        this.memberEmail = member.memberEmail;
    }



}
