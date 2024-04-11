package study.datajpa.repository;

import org.springframework.core.annotation.Order;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
