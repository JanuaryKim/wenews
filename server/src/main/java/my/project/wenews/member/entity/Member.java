package my.project.wenews.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 10)
    private String memberNickname;

    @Column(length = 12)
    private String memberPassword;

    @Column(length = 100)
    private String memberEmail;

    private Byte memberAge;


    public Member(Long memberId) {
        this.memberId = memberId;
    }
}
