package workout.workoutspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.domain.dto.request.UserJoinDTO;
import workout.workoutspring.domain.entity.User;
import workout.workoutspring.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String join(UserJoinDTO userJoinDTO) {
        Optional<User> findUser = userRepository.findOptionalByUsername(userJoinDTO.getUsername());
        if (findUser.isPresent()) {
            return "이미 존재하는 아이디";
        }

        User user = User.builder()
                .username(userJoinDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(userJoinDTO.getPassword()))
                .nickname(userJoinDTO.getNickname())
                .gender(userJoinDTO.getGender())
                .age(userJoinDTO.getAge())
                .height(userJoinDTO.getHeight())
                .weight(userJoinDTO.getWeight())
                .role(userJoinDTO.getRole())
                .state(true)
                .build();
        userRepository.save(user);
        return "회원가입 성공";
    }
}
