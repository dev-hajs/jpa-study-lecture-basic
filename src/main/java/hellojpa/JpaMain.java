package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // flush 직접 호출 -> 트랜잭션 종료 전에 query 가 나가는지 확인
//            Member member = new Member(200L, "member200");
//            em.persist(member);
//            em.flush();
//            System.out.println("===========");

            // 중간에 JPQL 쿼리 실행하는 경우 -> 잘못된 동작을 방지하기 위해 플러시가 자동 호출됩니다. 따라서 memberA, memberB, memberC 는 조회가 됩니다.
            Member memberA = new Member(3L, "memberA");
            Member memberB = new Member(4L, "memberB");
            Member memberC = new Member(5L, "memberC");
            em.persist(memberA);
            em.persist(memberB);
            em.persist(memberC);
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            for (Member member : members) {
                System.out.println("member.id = " + member.getId() + ", member.name = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
