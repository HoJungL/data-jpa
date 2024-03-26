package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

// 인터페이스 끼리 상속받을때는 extends를 슴!!
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsername(String username);

}
