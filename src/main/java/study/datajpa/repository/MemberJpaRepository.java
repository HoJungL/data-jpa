package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    @PersistenceContext // 영속성 컨텍스트라는 것.
    private EntityManager em;

    //저장
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    
    // 삭제
    public void delete(Member member) {
        em.remove(member);
    }

    // 전체 조회 일때는 ResultList
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }

    // 하나 조회
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    // 단건일때는 SingleResult
    public long count() {
        return em.createQuery("select count(m) from Member as m", Long.class)
                .getSingleResult();
    }
    // id 로 조회하기
    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
