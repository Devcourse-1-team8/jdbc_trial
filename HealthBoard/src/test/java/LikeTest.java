import org.health.domain.LikeDTO;
import org.health.repository.LikeRepository;
import org.health.repository.LikeRepositoryImpl;

public class LikeTest {
    public static void main(String[] args) {
        LikeRepository likeRepository = LikeRepositoryImpl.getInstance();

        // 삽입, 삭제
        LikeDTO like1 = new LikeDTO();
        like1.setLike_id(6);
        like1.setUser_id(1);
        like1.setBoard_id(3);

        int result1 = likeRepository.addLike(like1);
        System.out.println(result1 == 1 ? "좋아요 성공" : "좋아요 실패");
        int result2 = likeRepository.deleteLike(like1);
        System.out.println(result2 == 1 ? "좋아요 삭제 성공" : "좋아요 삭제 실패");

        // 오류 테스트
        LikeDTO like2 = new LikeDTO();
        like2.setUser_id(3);
        like2.setBoard_id(1);

        // 삽입 오류 테스트
        int result3 = likeRepository.addLike(like2);
        System.out.println(result3 == -1 ? "테스트 성공" : "테스트 실패");
        // 삭제 오류 테스트
        LikeDTO like3 = new LikeDTO();
        like3.setUser_id(2);
        like3.setBoard_id(2);
        int result4 = likeRepository.deleteLike(like3);
        System.out.println(result4 == -1 ? "테스트 성공" : "테스트 실패");
    }
}
