package workout.workoutspring.global.security.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User findByUsername = userRepository.findOptionalByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "유저를 찾지 못했습니다."));

        return new CustomUserDetails(findByUsername);
    }
}
