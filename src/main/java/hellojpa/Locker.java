package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {

    @Id @GeneratedValue
    private Long id;

    private String name;

    // 일대일 양방향 관계를 설정하기 위해서 추가
    @OneToOne(mappedBy = "locker")
    private Member member;
}
