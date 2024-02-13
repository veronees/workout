//package workout.workoutspring;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import workout.workoutspring.business.domain.entity.File;
//import workout.workoutspring.business.domain.entity.Post;
//import workout.workoutspring.business.domain.entity.User;
//import workout.workoutspring.business.domain.entity.Workout;
//import workout.workoutspring.business.repository.FileRepository;
//import workout.workoutspring.business.repository.PostRepository;
//import workout.workoutspring.business.repository.UserRepository;
//import workout.workoutspring.business.repository.WorkoutRepository;
//
//@Component
//@RequiredArgsConstructor
//public class Init {
//
//    private final WorkoutRepository workoutRepository;
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//    private final FileRepository fileRepository;
//
//    @PostConstruct
//    @Transactional
//    public void init() {
//        for (int i = 0; i < 20; i++) {
//            if (i % 2 == 0) {
//                User user = User.builder()
//                        .nickname("vero" + i)
//                        .build();
//                userRepository.save(user);
//
//
//                Post post = Post.builder()
//                        .user(user)
//                        .title("제목" + i)
//                        .content("내용" + i)
//                        .build();
//                postRepository.save(post);
//
//                Workout workout = Workout.builder()
//                        .workoutName("벤치프레스" + i)
//                        .target("chest")
//                        .build();
//                workoutRepository.save(workout);
//
//                File file = File.builder()
//                        .uploadFileName("uploadTestName" + i)
//                        .storeFileName("fhef-weiu-f3f4-aa2" + i + ".png")
//                        .post(post)
//                        .workout(workout)
//                        .build();
//                fileRepository.save(file);
//
//
//            }
//            else {
//                User user = User.builder()
//                        .nickname("vero" + i)
//                        .build();
//                userRepository.save(user);
//
//
//                Post post = Post.builder()
//                        .user(user)
//                        .title("제목" + i)
//                        .content("내용" + i)
//                        .build();
//                postRepository.save(post);
//
//                Workout workout = Workout.builder()
//                        .workoutName("랫풀다운" + i)
//                        .target("back")
//                        .build();
//                workoutRepository.save(workout);
//
//                File file = File.builder()
//                        .uploadFileName("uploadTestName" + i)
//                        .storeFileName("fhef-weiu-f3f4-aa2" + i + ".png")
//                        .post(post)
//                        .workout(workout)
//                        .build();
//                fileRepository.save(file);
//            }
//        }
//    }
//
//}
