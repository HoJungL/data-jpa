package study.datajpa.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

// 인터페이스 끼리 상속받을때는 extends를 슴!!
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {


    List<Member> findByUsername(String username);

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    /*
        @Query(name = "Member.findByUsername")
        List<Member> findByUsername(@Param("username") String username);
    */

    //실무에서 많이 씀!!
    //이녀석이 파라미터 바인딩이라는 녀석임. 말그대로 파라미터 묶어버리기.
    @Query("select m from Member as m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // 단순히 값 하나 조회
    @Query("select m.username from Member as m")
    List<String> findUsernameList();

    // DTO로 직접 조회
    // 반드시... 저 경로를 다써줘야함. 너무 귀찮은데.. 이건 나중에 해결.
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member as m join m.team t")
    List<MemberDto> findMemberDto();

    // 이게 컬렉션 파라미터 바인딩이라는거!
    @Query("select m from Member as m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); // 컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

//    Page<Member> findByAge(int age, Pageable pageable);

    // 이부분을 통해, 내가 개인적으로 countQuery를 만들 수 있음.
    /*@Query(value = "select m from Member as m left join m.team t",
            countQuery = "select count(m.username) from Member m") // countQuery 분리 가능!!
   */
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 이게 있어야 executeUpdate()를 인식함. 없으면 에러뜸
    @Query("update Member as m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // fetch에서 member를 조회할때, 연관된 team을 한방에! 가져옴
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // -> 더 쉽게! 한방에 가져오는 방법
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // JPQL에다가 EntityGraph를 넣을수 있음.
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

//    @EntityGraph(attributePaths = {"team"})
    //@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))가 entity에 있을경우
    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    // 너에게 힌트를 줄게! JPA Hint
    // 변경 자체가 안됨. (스냅샷이머지>?)
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    // where 마지막에 for update가 있음. 쿼리문을 보면 됨.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
