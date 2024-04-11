package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final EntityManager em;


    //이부분은 @RequiredArgsConstructor을 쓰면 해결됨.
    /*
        public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
    }*/

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member as m")
                .getResultList();
    }
}

