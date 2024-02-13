package workout.workoutspring.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.repository.UserRepository;
import workout.workoutspring.exception.LoginException;
import workout.workoutspring.exception.dto.ErrorCode;
import workout.workoutspring.global.security.domain.dto.LoginRequestDTO;
import workout.workoutspring.global.security.util.JwtUtil;
import workout.workoutspring.global.security.domain.dto.JwtDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.secret}")
    private String secretKey;

    public JwtDto loginProcess(LoginRequestDTO dto) {
        //username(ID) 일치하는지
        Optional<User> byUsername = userRepository.findOptionalByUsername(dto.getUsername());

        if(byUsername.isEmpty()) {
            throw new LoginException(ErrorCode.NO_EXIST_USER);
        }

        //비밀번호 맞는지
        User user = byUsername.get();
        if(!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new LoginException(ErrorCode.INCORRECT_PASSWORD);
        }

        //AccessToken, RefreshToken 발급
        JwtDto token = JwtUtil.createToken(user.getUsername(), secretKey);

        return token;
    }
}