import org.health.domain.BoardLikeDTO;
import org.health.repository.BoardRepository;
import org.health.repository.BoardRepositoryImpl;

public class BoardLikeFindTest {

    public static void main(String[] args) {
        // given
        BoardRepository instance = BoardRepositoryImpl.getInstance();
        int board_id = 1;
        int user_id = 1;

        // when
        BoardLikeDTO boardLikeDTO = instance.findById(board_id, user_id);

        // then
        if (boardLikeDTO.getLike_count() != 2) {
            System.out.println("assert failure");
        } else {
            System.out.println("assert success");
        }
        System.out.println(boardLikeDTO);
    }
}
