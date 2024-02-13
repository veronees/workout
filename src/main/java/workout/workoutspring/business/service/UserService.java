package workout.workoutspring.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import workout.workoutspring.business.domain.dto.request.PasswordDTO;
import workout.workoutspring.business.domain.dto.request.ProfileRequestDTO;
import workout.workoutspring.business.domain.dto.request.UserJoinDTO;
import workout.workoutspring.business.domain.dto.response.ProfileDTO;
import workout.workoutspring.business.domain.entity.Role;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.repository.UserRepository;
import workout.workoutspring.exception.LoginException;
import workout.workoutspring.exception.dto.ErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(UserJoinDTO userJoinDTO) {
        Optional<User> findUser = userRepository.findOptionalByUsername(userJoinDTO.getUsername());
        if (findUser.isPresent()) {
            throw new LoginException(ErrorCode.EXIST_ID);
        }

        User user = User.builder()
                .username(userJoinDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(userJoinDTO.getPassword()))
                .nickname(userJoinDTO.getNickname())
                .gender(userJoinDTO.getGender())
                .age(userJoinDTO.getAge())
                .height(userJoinDTO.getHeight())
                .weight(userJoinDTO.getWeight())
                .role(Role.valueOf(userJoinDTO.getRole()))
                .state(true)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(ProfileRequestDTO profileRequestDTO, Long userId) {
        User user = userRepository.findById(userId).get();
        user.changeNickname(profileRequestDTO.getNickname());
        user.updateLocation(profileRequestDTO.getLocation());
        user.updateWorkoutCareer(profileRequestDTO.getWorkoutCareer());
        user.update1RM(profileRequestDTO.getSquat(), profileRequestDTO.getBench(), profileRequestDTO.getDeadLift());
    }

    public ProfileDTO findUserInfo(Long userId) {
        User user = userRepository.findById(userId).get();
        ProfileDTO profileDTO = ProfileDTO.builder()
                .profileImageURL(fileUploadService.getPath(user.getFile().getStoreFileName()))
                .username(user.getUsername())
                .nickname(user.getNickname())
                .location(user.getLocation())
                .gender(user.getGender().toString())
                .squat(user.getSquat())
                .bench(user.getBench())
                .deadLift(user.getDeadLift())
                .workoutCareer(user.getWorkoutCareer())
                .build();
        return profileDTO;
    }

    @Transactional
    public void deleteUser(PasswordDTO passwordDTO, Long userId) {
        User user = userRepository.findUserById(userId);

        //비밀번호 맞는지
        if(!bCryptPasswordEncoder.matches(passwordDTO.getPassword(), user.getPassword())) {
            throw new LoginException(ErrorCode.INCORRECT_PASSWORD);
        }

        userRepository.delete(user);
    }

    public <T> void a(T hello) {

    }

    class a<T> {
        T h;
    }
}
