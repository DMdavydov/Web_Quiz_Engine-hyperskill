package engine.repository;

import engine.domain.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {

    @Query("SELECT c FROM CompletedQuiz c where c.user.email = :email order by c.completedAt desc")
    Page<CompletedQuiz> findAllByUserOrderByCompletedAtDesc(String email, Pageable pageable);
}
