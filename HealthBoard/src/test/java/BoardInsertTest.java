import org.health.domain.BoardDTO;
import org.health.repository.BoardRepository;
import org.health.repository.BoardRepositoryImpl;

import java.sql.Date;
import java.time.LocalDate;

import static java.time.LocalTime.now;

public class BoardInsertTest {
    public static void main(String[] args) {
        BoardRepository boardRepository = BoardRepositoryImpl.getInstance();

        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setBoard_id(2); //int
        boardDTO.setUser_id(1);  //int
        boardDTO.setNickname("park");
        boardDTO.setExercise_type("running");
        boardDTO.setExercise_time(40);    //int
        boardDTO.setMemo("너무 힘들었어요~");

        boardDTO.setCreate_at(LocalDate.now());
        boardDTO.setVisible(true);  //boolean

        boardRepository.changeVisible(boardDTO);

        for(BoardDTO b : boardRepository.findAll()){
            System.out.println(b);
        }


    }
}
