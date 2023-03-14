package my.project.wenews.member.dto;

import lombok.Getter;
import my.project.wenews.member.entity.Member;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {

        this.name = member.getMemberName();
        this.email = member.getMemberEmail();
        this.picture = member.getMemberPicture();
    }
}
