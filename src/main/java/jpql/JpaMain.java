package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //*************JPQL 기본문법
//            Member member = new Member();
//            member.setUsername("member1");
//            em.persist(member);
//
//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
//
//            System.out.println("result: " + result.getUsername());

            //*************프로젝션
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//            em.persist(member);
//
//            em.flush();
//            em.clear();

            //엔티티 타입
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

            //임베디드 타입
//            em.createQuery("select o.address from Order o", Address.class)
//                    .getResultList();

            //스칼라 타입
//            Query query = em.createQuery("select m.username, m.age from Member m");
//            List<Object[]> resultList = query.getResultList();

//            Object resultObject = resultList.get(0);
//            Object[] resultArr = (Object[]) resultObject; //Query타입 사용 시 변환 과정

//            Object[] resultArr = resultList.get(0); //제네릭 사용 시 이것만
//            System.out.println("username: " + resultArr[0]);
//            System.out.println("age: " + resultArr[1]);

//            List<MemberDto> resultList = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
//                    .getResultList();

//            MemberDto memberDto = resultList.get(0)
;
//            System.out.println("memberDto: " + memberDto.getUsername());
//            System.out.println("memberDto: " + memberDto.getAge());


//            Member findMember = result.get(0);
//            findMember.setAge(20);

            //****************페이징
//            for(int i = 0 ; i < 100; i++) {
//                Member member = new Member();
//                member.setUsername("member" + i);
//                member.setAge(i);
//                em.persist(member);
//            }
//
//
//            em.flush();
//            em.clear();
//
//            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            System.out.println("result.size: " + resultList.size());
//            for(Member member1 : resultList) {
//                System.out.println("member1: " + member1);
//            }

            //**************조인
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
////            String query = "select m from Member m inner join m.team t"; //내부 조인
////            String query = "select m from Member m left outer join m.team t"; //외부 조인
//            String query = "select m from Member m, Team t where m.username = t.name";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();

            //***************서브쿼리
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            String query = "select m from Member m where exists (select t from m.team t where t.name = 'TeamA')";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();

            //****************경로표현식
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

//            String query = "select m.username From Member m"; //상태필드. username에서 추가적인 탐색 블가
//            String query = "select m.team from Member m"; //단일값 필드
            String query = "select t.members from Team t"; //컬렉션 값 필드
//            String query = "select m.username from Team t join t.members m"; //컬렉션 값에서 추가탐색을 원한다면?

            Collection resultList = em.createQuery(query, Collection.class)
                    .getResultList();
            for(Object o : resultList) {
                System.out.println("object: " + o);
            }

//            Integer sizeResult = em.createQuery(query, Integer.class)
//                    .getSingleResult();
//            System.out.println("size: " + sizeResult);



            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
