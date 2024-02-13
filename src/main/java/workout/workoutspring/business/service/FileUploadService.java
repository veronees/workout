package workout.workoutspring.business.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import workout.workoutspring.business.domain.entity.File;
import workout.workoutspring.business.domain.entity.Post;
import workout.workoutspring.business.domain.entity.UploadFile;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.repository.PostRepository;
import workout.workoutspring.business.repository.FileRepository;
import workout.workoutspring.business.repository.UserRepository;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileUploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public UploadFile fileUpload(MultipartFile file) {
        try {
            String fileName = createStoreFileName(file.getOriginalFilename());//파일 원본 파일명

            //파일 확장자를 통해 Content-Type 유추
            String contentType = getContentTypeFromFileName(fileName);

            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(contentType);
            log.info("이 자식의 ContentType == : {}", file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
            return new UploadFile(file.getOriginalFilename(), fileName);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패");
        }
    }



    @Transactional
    public void saveFilePostInfo(UploadFile uploadFile, Long postId) {
        Post post = postRepository.findById(postId).get();
        File file = File.builder()
                .uploadFileName(uploadFile.getUploadFileName())
                .storeFileName(uploadFile.getStoreFileName())
                .post(post)
                .build();

        fileRepository.save(file);
    }

    @Transactional
    public void saveFileProfileInfo(UploadFile uploadFile, Long userId) {
        User user = userRepository.findById(userId).get();
        File file = File.builder()
                .uploadFileName(uploadFile.getUploadFileName())
                .storeFileName(uploadFile.getStoreFileName())
                .user(user)
                .build();

        fileRepository.save(file);
    }

    public String getPath(String imagePath) {
        String url = amazonS3Client.getUrl(bucket, imagePath).toString();
        return url;
    }


    //==편의 메서드==//
    private String getContentTypeFromFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

        // 간단한 매핑 로직을 통해 Content-Type 결정 (필요에 따라 더 확장 가능)
        switch (extension.toLowerCase()) {
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            default:
                return "application/octet-stream"; // 기본적으로 바이너리 데이터로 처리
            //멀티파트는 임시로 파일을 디스크에 쓰기때문에, 업로드 할 파일이 많거나 용량이 크면 사용하지 않는게 좋습니다
            //octet stream 추천드려요
        }
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;

    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }
}
