package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name"})
public class Team extends JpaBaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // fk 없는 곳에 쓰는게 좋음!
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

}
