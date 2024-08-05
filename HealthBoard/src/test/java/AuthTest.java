import org.health.domain.UserSignUpDTO;
import org.health.infra.AuthManager;
import org.health.repository.UserRepository;
import org.health.repository.UserRepositoryImpl;

public class AuthTest {
    public static void main(String[] args) {
        UserRepository repository = UserRepositoryImpl.getInstance();
        addUserTest(repository);
        loginTest(repository);
        logoutTest(repository);
    }

    private static void addUserTest(UserRepository repository) {
        UserSignUpDTO newUser = new UserSignUpDTO("test user", "여자", 25);
        int actual = repository.addUser(newUser);
        int expected = 1;
        System.out.println(actual == expected ? "사용자 등록 성공" : "사용자 등록 실패");
    }

    private static void loginTest(UserRepository repository) {
        try {
            String nickname = "test user";
            repository.login(nickname);
            System.out.println("로그인 성공");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void logoutTest(UserRepository repository) {
        repository.logout();
        try {
            AuthManager authManager = AuthManager.getInstance();
            System.out.println(authManager.getCurrentUser());
        } catch (RuntimeException e) {
            System.out.println("로그아웃 성공");
        }
    }
}
