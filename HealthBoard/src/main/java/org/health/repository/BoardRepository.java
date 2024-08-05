package org.health.repository;

import org.health.domain.BoardDTO;
import org.health.domain.BoardLikeDTO;

import java.util.List;

public interface BoardRepository {

    int addBoard(BoardDTO board);
    int changeVisible(int boardId);
    List<BoardDTO> findByUser_id(int user_id);
    List<BoardDTO> findAll();
    BoardLikeDTO findById(int board_id, int user_id);
}
