package org.health.view;

import org.health.domain.BoardDTO;
import org.health.domain.BoardLikeDTO;
import org.health.domain.LikeDTO;
import org.health.domain.UserSignUpDTO;
import org.health.infra.AuthManager;
import org.health.repository.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {



    private static UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static BoardRepository boardRepository = BoardRepositoryImpl.getInstance();
    private static LikeRepository likeRepository = LikeRepositoryImpl.getInstance();


    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        while (true) {
            try {
                printMenu();
                int select = Integer.parseInt(br.readLine());

                switch (select) {
                    case 1:
                        registerUser(br);
                        break;
                    case 2:
                        loginUser(br);
                        break;
                    case 3:
                        logoutUser();
                        break;
                    case 4:
                        viewAllUsers();
                        break;
                    case 5:
                        viewAllBoardRecords();
                        break;
                    case 6:
                        viewPersonalBoardRecords(br);
                        break;
                    case 7:
                        viewBoardRecordDetails(br);
                        break;
                    case 8:
                        addBoardRecord(br);
                        break;
                    case 9:
                        deleteBoardRecord(br);
                        break;
                    case 10:
                        toggleLike(br);
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printMenu() {
        System.out.println("HEALTH BOARD");
        System.out.println("1. 사용자 등록");
        System.out.println("2. 사용자 로그인");
        System.out.println("3. 사용자 로그아웃");
        System.out.println("4. 전체 사용자 조회 (관리자)");
        System.out.println("5. 전체 운동 기록 조회");
        System.out.println("6. 개인 운동 기록 열람");
        System.out.println("7. 운동 기록 상세 조회");
        System.out.println("8. 운동 기록 추가");
        System.out.println("9. 운동 기록 삭제");
        System.out.println("10. 운동 기록에 좋아요 표시/해제");
        System.out.println("0. 종료");
        System.out.print(" >>  ");
    }

    private static void registerUser(BufferedReader reader) throws IOException {
        System.out.print("닉네임을 입력하세요: ");
        String nickname = reader.readLine();
        System.out.print("나이를 입력하세요: ");
        int age = Integer.parseInt(reader.readLine());
        System.out.print("성별을 입력하세요: ");
        String gender = reader.readLine();
        UserSignUpDTO user = new UserSignUpDTO(nickname, gender, age);
        userRepository.addUser(user);
    }

    private static void loginUser(BufferedReader reader) throws IOException {
        System.out.print("닉네임을 입력하세요: ");
        String nickname = reader.readLine();
        userRepository.login(nickname);
        int userId = AuthManager.getInstance().getCurrentUser();
        userRepository.isConsecutiveForTest(userId);
    }

    private static void logoutUser() {
        int userId = AuthManager.getInstance().getCurrentUser();
        userRepository.updateLoginDate(userId, LocalDateTime.now());
        userRepository.logout();
        System.out.println("로그아웃");
    }

    private static void viewAllUsers() throws SQLException {
        userRepository.findAll().forEach(System.out::println);
    }

    private static void viewAllBoardRecords() {
        boardRepository.findAll().forEach(System.out::println);
    }

    private static void viewPersonalBoardRecords(BufferedReader reader) throws IOException {
        System.out.print("닉네임을 입력하세요: ");
        String nickname = reader.readLine();
        //  << 사용자 ID 조회 로직 넣기
        int userId = 1;
        boardRepository.findByUser_id(userId).forEach(System.out::println); // toString으로 출력
    }

    private static void viewBoardRecordDetails(BufferedReader reader) throws IOException {
        System.out.print("운동 기록 ID를 입력하세요: ");

        int boardId = Integer.parseInt(reader.readLine());
        int userId = AuthManager.getInstance().getCurrentUser();

        System.out.println(boardRepository.findById(boardId, userId));
    }

    private static void addBoardRecord(BufferedReader reader) throws IOException {
        System.out.print("운동 종류를 입력하세요: ");
        String exerciseType = reader.readLine();
        System.out.print("운동 시간을 입력하세요: ");
        int exerciseTime = Integer.parseInt(reader.readLine());
        System.out.print("기록 내용을 입력하세요: ");
        String content = reader.readLine();

        int userId = AuthManager.getInstance().getCurrentUser();
        BoardDTO board = new BoardDTO(0, userId, "별명",exerciseType, exerciseTime, content, null, true);
        boardRepository.addBoard(board);
    }

    private static void deleteBoardRecord(BufferedReader reader) throws IOException {
        System.out.print("운동 기록 ID를 입력하세요: ");
        int boardId = Integer.parseInt(reader.readLine());
        int userId = AuthManager.getInstance().getCurrentUser();
        BoardLikeDTO board = boardRepository.findById(boardId , userId);
        if (board != null) {
            boardRepository.changeVisible(board);
        }
    }

    private static void toggleLike(BufferedReader reader) throws IOException {
        System.out.print("운동 기록 ID를 입력하세요: ");
        int boardId = Integer.parseInt(reader.readLine());
        System.out.print("닉네임을 입력하세요: ");
        String nickname = reader.readLine();
        // 사용자 ID 조회 로직 필요
        int userId = AuthManager.getInstance().getCurrentUser();

        LikeDTO like = new LikeDTO(0, userId, boardId);
        if (likeRepository.checkExist(like)) {
            likeRepository.deleteLike(like);
        } else {
            likeRepository.addLike(like);
        }
    }
}