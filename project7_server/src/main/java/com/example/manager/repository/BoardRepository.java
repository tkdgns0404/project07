package com.example.manager.repository;

import com.example.manager.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Using JPA
@Repository
public interface BoardRepository extends JpaRepository<Board, String> {


    /**
     * 작성자로 게시글 조회
     * @param writer 조회할 작성자이름
     * @return 조회 결과(여러글을 쓸 수 있으니 List)
     */
    List<Board> findByWriter(String writer);
    
    Board findByIdx(int idx);
    
    void deleteByIdx(int idx);
    
}
