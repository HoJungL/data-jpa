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

# @EnableJpaAuditing
스프링부트 설정 클래스에 적용을 해야  스프링 데이터 JPA가 실행이 됨.

# @EntityListeners(AuditingEntityListener.class)
엔티티에 적용을 해야함.

# String 쓰는법
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
를 쓴 뒤에, Application에
    @Bean
    public AuditorAware<String> auditorProvider() {
    return () -> Optional.of(UUID.randomUUID().toString());
    }
를 써야함!

# Error 
중요함. 이걸로 한시간 휘리릭.
- DTO에 게터/세터 안쓰니까, map이 안먹힘. 반드시 map을 넣을것.
- https://stackoverflow.com/questions/8367312/serializing-with-jackson-json-getting-no-serializer-found


## save()메소드
- 새로운 엔티티면 저장('persist')
- 새로운 엔티티가 아니면 병합('merge')

- --> 문자같은걸로 들어가면 merge로 인식해버림. 그러면 큰일남....
- --> Persistable해서 새로운 객체를 하나 만들어서 null로 생성햬야함.
- 이부분은 ItemRepositoryTest 부분을 참고할것.

### 판단 기준
- 식별자가 객체일때 null로 판단
- 식별자가 자바 기본타입일때 0으로 판단
- persistable  인터페이스를 구현해서 판단 로직 변경 가능.