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

            // insert
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUserName("member1");
//            member.setTeamId(team.getId()); //-> '객체지향'스럽지 않은 코드!
            member.setTeam(team);
            em.persist(member);

            /*
            * 1차 캐시가 아닌 DB 에서 조회하는걸 보고 있다면..
            em.flush();
            em.clear();
            */

            // select
            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId(); //-> '객체지향'스럽지 않은 코드!
//            Team findTeam = em.find(Team.class, findTeamId);
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam.name = " + findTeam.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
