package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setUserName("hello1");
            em.persist(member1);

            em.flush();
            em.clear();

            // proxy, entity 비교 (==, instance of)
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass()); // proxy
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass()); // member
//            System.out.println("a == a:" + (refMember == findMember)); // 성립x. 따라서 findMember 도 proxy 로 변환됩니다.

            // 영속성 컨텍스트에 없는(준영속 상태의) proxy 초기화 -> 예외 발생 확인
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember.getClass() = " + refMember.getClass()); // proxy
//            em.detach(refMember);
//            em.clear();
//            refMember.getUserName();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass()); // proxy
//            refMember.getUserName(); // proxy 강제 초기화 - 1
//            Hibernate.initialize(refMember); // proxy 강제 초기화 - 2
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("==================================================");
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(Member m1, Member m2) {
        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
        System.out.println("m1 instanceof Member: " + (m1 instanceof Member));
        System.out.println("m2 instanceof Member: " + (m2 instanceof Member));
    }

}
