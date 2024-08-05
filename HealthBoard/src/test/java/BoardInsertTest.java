import org.health.domain.BoardDTO;
import org.health.repository.BoardRepository;
import org.health.repository.BoardRepositoryImpl;

public class BoardInsertTest {
    public static void main(String[] args) {
        BoardRepository boardRepository = BoardRepositoryImpl.getInstance();

        BoardDTO board = new BoardDTO();
        board.setBoard_id(100);
        board.setUser_id(3);
        board.setExercise_type("운동 타입 테스트");
        board.setExercise_time(1000);
        board.setMemo("운동 테스트 중");

        int result = boardRepository.addBoard(board);
        System.out.println(result != 0 ? "게시글 추가 성공" : "게시글 추가 실패");
    }
}
