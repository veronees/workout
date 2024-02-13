package workout.workoutspring.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.business.domain.dto.response.RequestLogResponseDTO;
import workout.workoutspring.business.domain.entity.RequestLog;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.repository.RequestLogRepository;
import workout.workoutspring.business.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;
    private final UserRepository userRepository;

    @Transactional
    public void requestSend(Long userId, String username) {
        User user = userRepository.findUserById(userId); //요청을 받은 유저
        User requestUser = userRepository.findByUsername(username); //요청을 한 유저

        RequestLog requestLog = RequestLog.builder()
                .requestUsername(username)
                .requestUserNickname(requestUser.getNickname())
                .user(user)
                .build();
        requestLogRepository.save(requestLog);

    }

    public List<RequestLogResponseDTO> findRequestList(Long userId) {
        User user = userRepository.findUserById(userId);
        List<RequestLog> requestLogs = user.getRequestLogs();
        List<RequestLogResponseDTO> requestLogResponseDTOS = new ArrayList<>();
        for (RequestLog requestLog : requestLogs) {
            RequestLogResponseDTO requestLogResponseDTO = RequestLogResponseDTO.builder()
                    .requestUsername(requestLog.getRequestUsername())
                    .requestUserNickname(requestLog.getRequestUserNickname())
                    .build();
            requestLogResponseDTOS.add(requestLogResponseDTO);
        }
        return requestLogResponseDTOS;
    }
}
