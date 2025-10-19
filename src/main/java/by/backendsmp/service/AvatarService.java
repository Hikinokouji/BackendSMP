package by.backendsmp.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucket;

    public void uploadAvatar(String username, MultipartFile file) {
        try{
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(username + "/" + "avatar")
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }catch(Exception e){
            throw new RuntimeException("Upload failed", e);
        }
    }

    public void uploadAvatarProfiles(String object, MultipartFile file) {
        try{
            //String extension = getExtension(file.getOriginalFilename());
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            //.object(object + extension)
                            .object(object)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }catch(Exception e){
            throw new RuntimeException("Upload failed", e);
        }
    }

    public Map<String, String> getAvatar(Long id, String param) {
        try{
            String objectName;
            checheOnNotEmptyInBucket(id, param);
            if(param.equals("avatar")){
                objectName = id.toString() + "/" + param;
                Map<String, String> presignedUrl = new HashMap<>();
                presignedUrl.put("userAvatar", minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucket)
                                .object(objectName)
                                .build()
                ));
                return presignedUrl;

            }else if(param.equals("profile")){
                objectName = id.toString() + "/" + param;
                Map<String, String> presignedUrl = new HashMap<>();
                presignedUrl.put("profileAvatar", minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucket)
                                .object(objectName)
                                .build()
                ));
                return presignedUrl;
            }else if(param.equals("full_avatar")){
                objectName = id.toString() + "/";
                Map<String, String> presignedUrl = new HashMap<>();
                presignedUrl.put("userAvatar", minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucket)
                                .object(objectName + "avatar")
                                .build()
                ));
                presignedUrl.put("profileAvatar", minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucket)
                                .object(objectName + "profile")
                                .build()
                ));
                return presignedUrl;
            }
            throw new RuntimeException("Unknown parameter");
        }catch(Exception e){
            throw new RuntimeException("Get presigned object failed", e);
        }
    }

    public void checheOnNotEmptyInBucket(Long id, String param){
        try{
            String objectName = id.toString() + "/";
            if(param.equals("avatar")){
                minioClient.statObject(
                        StatObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName + "avatar")
                                .build()
                );
            }else if(param.equals("profile")){
                minioClient.statObject(
                        StatObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName + "profile")
                                .build()
                );
            } else if (param.equals("full_avatar")) {
                minioClient.statObject(
                        StatObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName + "avatar")
                                .build()
                );
                minioClient.statObject(
                        StatObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName + "profile")
                                .build()
                );
            }
        }catch(ErrorResponseException e){
            if (e.errorResponse().code().equals("NoSuchKey")) {
                throw new RuntimeException("NoSuchKey", e);
            }
            throw new RuntimeException("Error checking avatar", e);
        }catch(Exception e){
            throw new RuntimeException("Get presigned object failed", e);
        }
    }
}
