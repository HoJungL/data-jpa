TEST에서 오래걸리는 이유
- gradle을 거쳐서 가기 때문에 오래걸림
->  build를 gradle이 아닌, intellJ로 변경할것.

Lombok을 적용하기 위해서는  반드시,setting -> annotation 에서 enable을 체킹 해줄것.

벌크 연산은 영속성 컨텍스트에는 연관이 안되고, DB에만 반영이 됨.
따라서, 벌크연산 이후에는 영속성 컨텍스트를 다 날려버려야함.
-> em.flush(), em.clear()를 해야함 -> 영솏성 컨텍스트가 깨끗해지고 -> 그 후에 다시 불러올때, DB에서 영속성 컨텍스트에서 가져오니까 벌크 연산후의 DATA가 불러와짐
data jpa는 @Modifying(clearAutomatically = true)를 쓰면 em.clear를 안해도됨.


*패치조인!(@EntityGraph)*
- 기본 형태 : @query를 써서 쿼리문 짜야함
- JPA 사용시 (fetch) : 쿼리 짜고 join 뒤에 fetch를 써줌
- @EntityGraph : attributePaths= {""} 쓰면 끝


-- 사용자 정의 Repository 구현
어렵지 않음. 그냥 내가 새로 만들면 됨.(MemberRepositoryCustom,Impl 참고)
이건 보통 querydsl에서 많이 사용할 예정임
단, MemberRepository에다가 Impl로 이름을 꼭 맞춰야함.(관례임)

스프링 빈으로 등록해서 직접 사용해도 무관(반드시 리포지토리를 만들필요가 없다는 소리! - MemberQueryRepository 참고)


# Auditing
속성을 테이블에 그냥 쓸수만 있게하는 방법 -> @MappedSuperclass
(JpaBaseEntity 참고)