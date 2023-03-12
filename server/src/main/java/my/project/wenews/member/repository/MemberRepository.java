package my.project.wenews.member.repository;

import my.project.wenews.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByMemberEmail(String memberEmail);
}
