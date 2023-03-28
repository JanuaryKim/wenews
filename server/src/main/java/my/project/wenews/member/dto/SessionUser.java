package my.project.wenews.member.dto;

import lombok.Getter;
import my.project.wenews.member.entity.Member;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 7710501663143450656L; //local class incompatible 에러 방지, 사전 지정

    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {

        this.id = member.getMemberId();
        this.name = member.getMemberName();
        this.email = member.getMemberEmail();
        this.picture = member.getMemberPicture();
    }
}
