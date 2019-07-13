package pl.sda.mysampleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysampleblog.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
