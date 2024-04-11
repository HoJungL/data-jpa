package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter // setter는 일반적으로 안씀..!
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 기준
// (team은 넣으면 무한루프뜰수 있으니 주의)
@ToString(of = {"id", "username", "age"}) //객체 찍을때 나오는 출력값 저장하기
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username =:username"
)
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member extends JpaBaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
