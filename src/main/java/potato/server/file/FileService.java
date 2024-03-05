package potato.server.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author 정순원
 * @since 2023-10-16
 */
@Service
@RequiredArgsConstructor
public class FileService {

    private static final String PREFIX = "uglypotato-bucket";
    private final AmazonS3Client amazonsS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * presigned url 발급
     *
     * @param fileName 클라이언트가 전달한 파일명 파라미터
     * @return presigned url
     */
    public String getPreSignedUrl(String fileName) {
        String path = createPath(fileName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, path);
        URL url = amazonsS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    /**
     * 파일 업로드용(PUT) presigned url 생성
     *
     * @param bucket 버킷 이름
     * @param path   S3 업로드용 파일 이름
     * @return presigned url
     */
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String path) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, path)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    /**
     * presigned url 유효 기간 설정
     *
     * @return 유효기간
     */
    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    /**
     * 파일의 전체 경로를 생성
     *
     * @return 파일의 전체 경로
     */
    private String createPath(String fileName) {
        String fileId = createFileId();
        return String.format("%s/%s", PREFIX, fileId + fileName);
    }


    /**
     * 파일 고유 ID를 생성
     *
     * @return 36자리의 UUID
     */
    private String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 파일 직접 삭제
     */
    public void deleteFile(String path) {
        try {
            amazonsS3Client.deleteObject(bucket, path);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}