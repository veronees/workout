package workout.workoutspring.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.business.domain.dto.request.AcceptRequestDTO;
import workout.workoutspring.business.domain.dto.response.AcceptResponseDTO;
import workout.workoutspring.business.domain.entity.Accept;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.repository.AcceptRepository;
import workout.workoutspring.business.repository.RequestLogRepository;
import workout.workoutspring.business.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AcceptService {

    private final AcceptRepository acceptRepository;
    private final RequestLogRepository requestLogRepository;
    private final UserRepository userRepository;

    //요청을 수락하면 요청이 온 페이지에서는 해당 행이 지워지고, 수락한 요청 보이는 리스트에 보여야함.
    //그럼 reqeustLog에서는 지우고, accept에 추가

    @Transactional
    public void accept(AcceptRequestDTO acceptRequestDTO, String username) {
        User user = userRepository.findByUsername(username);
        requestLogRepository.deleteByUserId(user.getId());
        Accept accept = Accept.builder()
                .acceptUsername(acceptRequestDTO.getRequestUsername())
                .acceptUserNickname(acceptRequestDTO.getRequestUserNickname())
                .user(user)
                .status(false)
                .build();
        acceptRepository.save(accept);
    }

    public List<AcceptResponseDTO> findAccept(String username) {
        User user = userRepository.findByUsername(username);
        List<Accept> accepts = user.getAccepts();
        List<AcceptResponseDTO> acceptResponseDTOS = new ArrayList<>();
        for (Accept accept : accepts) {
            if (accept.isStatus() == false) {
                AcceptResponseDTO acceptResponseDTO = AcceptResponseDTO.builder()
                        .requestUsername(accept.getAcceptUsername())
                        .requestUserNickname(accept.getAcceptUserNickname())
                        .build();
                acceptResponseDTOS.add(acceptResponseDTO);
            }
        }
        return acceptResponseDTOS;
    }

    @Transactional
    public void completeAccept(String username, AcceptRequestDTO acceptRequestDTO) {
        User user = userRepository.findByUsername(username); //요청 한 유저
        Accept accept = acceptRepository.findAcceptByUserIdAndStatusIsFalse(user.getId(), acceptRequestDTO.getRequestUsername());
        accept.updateToTrue();
    }
}
