package ru.otus.jpql;

import org.hibernate.LazyInitializationException;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.jpql.model.Avatar;
import ru.otus.jpql.model.Course;
import ru.otus.jpql.model.EMail;
import ru.otus.jpql.model.OtusStudent;
import ru.otus.mainops.model.Category;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.core.HibernateUtils.buildSessionFactory;
import static ru.otus.core.HibernateUtils.doInSessionWithTransaction;

class JpqlTest {

    private static final int EXPECTED_NUMBER_OF_STUDENTS = 10;
    private static final int EXPECTED_QUERIES_COUNT = 31;

    private static final long FIRST_STUDENT_ID = 1L;
    private static final long FIRST_EMAIL_ID = 1L;

    private static final String FIRST_STUDENT_NAME = "AnyName1";

    private SessionFactory sf;

    private static OtusStudent makeStudentByNumAndId(long id, int num) {
        var avatar = new Avatar(id, String.format("http://any-addr%d.ru/", num));
        var eMail = new EMail(id, String.format("any@addr%d.ru", num));
        var course = new Course(id, String.format("Course№%d", num));
        return new OtusStudent(id, String.format("AnyName%d", num),
                avatar, List.of(eMail), List.of(course));
    }

    @BeforeEach
    void setUp() {
        sf = buildSessionFactory(OtusStudent.class, Avatar.class, EMail.class,
                Course.class, Category.class);
        sf.getStatistics().setStatisticsEnabled(true);

        for (int i = 1; i <= 10; i++) {
            var student = makeStudentByNumAndId(0, i);
            doInSessionWithTransaction(sf, session -> session.persist(student));
        }

        for (int i = 1; i <= 3; i++) {
            var parentCategory = new Category(0, "Parent category #" + i, null);
            var childCategory = new Category(0, "Child category #" + i, parentCategory);
            doInSessionWithTransaction(sf, session -> session.persist(childCategory));
        }
    }


    @DisplayName(" должен загружать информацию о нужном студенте по его имени")
    @Test
    void shouldFindExpectedStudentByName() {
        doInSessionWithTransaction(sf, session -> {

            var query = session.createQuery("select s " +
                    "from OtusStudent s " +
                    "where s.name = :name", OtusStudent.class);

            query.setParameter("name", FIRST_STUDENT_NAME);

            var students = query.getResultList();

            assertThat(students).usingRecursiveFieldByFieldElementComparator()
                    .containsOnlyOnce(makeStudentByNumAndId(FIRST_STUDENT_ID, 1));
        });
    }

    @DisplayName(" должен изменять имя заданного студента по его id")
    @Test
    void shouldUpdateStudentNameById() {
        doInSessionWithTransaction(sf, session -> {
            var firstStudent = session.find(OtusStudent.class, FIRST_STUDENT_ID);
            var oldName = firstStudent.getName();
            var newName = firstStudent.getName() + firstStudent.getName();
            session.detach(firstStudent);

            var query = session.createMutationQuery("update OtusStudent s " +
                    "set s.name = :name " +
                    "where s.id = :id");
            query.setParameter("id", FIRST_STUDENT_ID);
            query.setParameter("name", newName);
            query.executeUpdate();

            var updatedStudent = session.find(OtusStudent.class, FIRST_STUDENT_ID);

            assertThat(updatedStudent.getName()).isNotEqualTo(oldName).isEqualTo(newName);
        });
    }

    @DisplayName(" должен удалять заданный email по его id")
    @Test
    void shouldDeleteStudentNameById() {
        doInSessionWithTransaction(sf, session -> {
            var firstEMail = session.find(EMail.class, FIRST_EMAIL_ID);
            assertThat(firstEMail).isNotNull();
            session.detach(firstEMail);

            var query = session.createMutationQuery("delete " +
                    "from EMail e " +
                    "where e.id = :id");
            query.setParameter("id", FIRST_EMAIL_ID);
            query.executeUpdate();

            var deletedEMail = session.find(EMail.class, FIRST_EMAIL_ID);

            assertThat(deletedEMail).isNull();
        });
    }

    @DisplayName("должен загружать список всех студентов с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        sf.getStatistics().clear();

        doInSessionWithTransaction(sf, session -> {

            //EntityGraph<?> entityGraph = session.getEntityGraph("otus-student-avatars-entity-graph");
            var query = session.createQuery("select s from OtusStudent s", OtusStudent.class);
            //var query = session.createQuery("select distinct s from OtusStudent s join fetch s.emails", OtusStudent.class);
            //query.setHint("jakarta.persistence.fetchgraph", entityGraph);

            var students = query.getResultList();

            assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_STUDENTS)
                    .allMatch(s -> !s.getName().equals(""))
                    .allMatch(s -> s.getCourses() != null && s.getCourses().size() > 0)
                    .allMatch(s -> s.getAvatar() != null)
                    .allMatch(s -> s.getEmails() != null && s.getEmails().size() > 0);
        });
        assertThat(sf.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("должен загружать ожидаемый список студентов по номеру страницы")
    @Test
    void shouldReturnCorrectStudentsListByPage() {
        sf.getStatistics().clear();

        doInSessionWithTransaction(sf, session -> {
            var studentsCount = session.createQuery("select count(s) from OtusStudent s", Long.class).getSingleResult();
            var pageNum = 2;
            var pageSize = 3;
            var pagesCount = (long) Math.ceil(studentsCount * 1d / pageSize);

            var query = session.createQuery("select s from OtusStudent s ", OtusStudent.class);
/*
            // Так не будет offset + limit из-за того, что студент может занимать больше одной строки набора данных
            var query = session.createQuery("select s " +
                            "from OtusStudent s join fetch s.courses c",
                    OtusStudent.class);
*/
            var students = query.setFirstResult(pageNum * pageSize).setMaxResults(pageSize).getResultList();

            assertThat(pagesCount).isEqualTo(4);
            assertThat(students).isNotNull().hasSize(pageSize);
        });
    }

    @DisplayName(" должен загружать все категории без выброса исключения")
    @Test
    void shouldLoadAllCategoriesWithoutException() {
        List<Category> categories = new ArrayList<>();
        doInSessionWithTransaction(sf, session -> {
            var query = session.createQuery("select c from Category c", Category.class);
            categories.addAll(query.getResultList());
        });
        assertThatCode(() -> {
            categories.forEach(System.out::println);
        }).doesNotThrowAnyException();
    }

    @DisplayName(" должен выбрасывать LazyInitializationException при загрузке дочерних категорий")
    @Test
    void shouldLoadChildCategoriesWithLazyInitializationException() {
        List<Category> categories = new ArrayList<>();
        doInSessionWithTransaction(sf, session -> {
            var query = session.createQuery("select c from Category c where c.parent is not null", Category.class);
            categories.addAll(query.getResultList());
        });
        assertThatCode(() -> {
            categories.forEach(System.out::println);
        }).isExactlyInstanceOf(LazyInitializationException.class);
    }
}