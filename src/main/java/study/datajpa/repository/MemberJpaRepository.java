package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

@Repository
public class MemberJpaRepository {

    @PersistenceContext // 영속성 컨텍스트라는 것.
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    // id 로 조회하기
    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
