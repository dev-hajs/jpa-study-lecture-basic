package hellojpa;

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
            // 동일성 보장 확인
//            Member findMember1 = em.find(Member.class, 101L);
//            Member findMember2 = em.find(Member.class, 101L);
//            System.out.println("result = " + (findMember1 == findMember2));

            // 쓰기 지연 확인
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//            em.persist(member1);
//            em.persist(member2);
//            System.out.println("=============");
//             tx.commit();

            // dirty checking 확인
//            Member findMember = em.find(Member.class, 150L);
//            findMember.setName("ZZZZZ");
//            System.out.println("=============");

            // entity 삭제 (내가 궁금해서 짠 코드! -> 삭제는 RDBMS 에서 auto commit 이 되는 작업이다. JPA 에서는 어떻게 될까? => 트랜잭션이 끝날 때 작업한다! (전제조건을 보장하는구나))
//            Member findMember = em.find(Member.class, 160L);
//            em.remove(findMember);
//            System.out.println("=============");

            // entity 추가와 삭제 (내가 궁금해서 짠 코드! -> 한 트랜잭션 안에서 객체를 생성하고 제거하면 1차 캐시 내에서만 핸들링 되는걸까? => 쓰기 지연 SQL 저장소에 insert, delete query 가 적재되었고 실행됐다!)
            Member memberHa = new Member(1000L, "하");
            em.persist(memberHa);
            System.out.println("=============");
            em.remove(memberHa);
            System.out.println("=============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
