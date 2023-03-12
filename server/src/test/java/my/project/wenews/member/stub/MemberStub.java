package my.project.wenews.member.stub;

import my.project.wenews.member.entity.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberStub {

    public static Map<String,Object> memberDtoMap = new HashMap<>();
    public static List<Member> memberList;

    static {

        Member member = new Member();
        member.setMemberId(1L);
        member.setMemberEmail("wot00@naver.com");

        memberList = List.of(member);
    }
}
