TEST에서 오래걸리는 이유
- gradle을 거쳐서 가기 때문에 오래걸림
->  build를 gradle이 아닌, intellJ로 변경할것.

Lombok을 적용하기 위해서는  반드시,setting -> annotation 에서 enable을 체킹 해줄것.

벌크 연산은 영속성 컨텍스트에는 연관이 안되고, DB에만 반영이 됨.
따라서, 벌크연산 이후에는 영속성 컨텍스트를 다 날려버려야함.
-> em.flush(), em.clear()를 해야함 -> 영솏성 컨텍스트가 깨끗해지고 -> 그 후에 다시 불러올때, DB에서 영속성 컨텍스트에서 가져오니까 벌크 연산후의 DATA가 불러와짐
data jpa는 @Modifying(clearAutomatically = true)를 쓰면 em.clear를 안해도됨.


*패치조인!(@EntityGraph)*
