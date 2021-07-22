package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /* JPQL START */
            // JPQL
//            List<Member> resultList = em.createQuery(
//                "SELECT m FROM Member m WHERE m.userName like '%kim%'"
//                , Member.class
//            ).getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }
            /* JPQL END */

            /* Criteria START */
            // Criteria 시작
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
              // 루트 클래스 (조회를 시작할 클래스)
//            Root<Member> m = query.from(Member.class);
//
              // (동적)쿼리 생성
//            CriteriaQuery<Member> cq = query.select(m);
//
//            String userName = "asfasf";
//            if (userName != null) {
//                cq = cq.where(cb.equal(m.get("userName"), "kim"));
//            }
//            List<Member> resultList = em.createQuery(cq).getResultList();
            /* Criteria END */

            /* NativeQuery START */
//            em.createNativeQuery("SELECT MEMBER_ID, city, street, zipcode, USERNAME FROM MEMBER").getResultList();
            /* NativeQuery END */

            Member member = new Member();
            member.setUserName("member1");
            em.persist(member);
            // flush 되는 시점 -> commit, query
            List<Member> resultList = em.createNativeQuery("SELECT MEMBER_ID, city, street, zipcode, USERNAME FROM MEMBER", Member.class).getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

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
