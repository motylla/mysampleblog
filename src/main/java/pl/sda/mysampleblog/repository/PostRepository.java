package pl.sda.mysampleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysampleblog.model.Post;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {
    Post findFirstByTitle(String title);
//    List<Post> findAllOrderByIdDesc();
}
