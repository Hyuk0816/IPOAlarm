package com.sideprj.ipoAlarm.domain.user.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.EncryptedPutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sideprj.ipoAlarm.domain.user.constants.UserConstants;
import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.mapper.UserMapper;
import com.sideprj.ipoAlarm.domain.user.repository.UserRepository;
import com.sideprj.ipoAlarm.domain.user.role.ROLE;
import com.sideprj.ipoAlarm.domain.user.service.UserService;
import com.sideprj.ipoAlarm.domain.user.vo.UserDetailsRequestVo;
import com.sideprj.ipoAlarm.util.exception.UsersAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public void createUsers(UserDetailsRequestVo userDetailsRequestVo) {

        var checkUserVo = UserDetailsRequestVo.builder()
                .email(userDetailsRequestVo.getEmail())
                .nickName(userDetailsRequestVo.getNickName())
                .password(passwordEncoder.encode(userDetailsRequestVo.getPassword()))
                .image(userDetailsRequestVo.getImage())
                .build();

        Optional<User> optionalUsers = userRepository.findByEmail(checkUserVo.getEmail());
        if (optionalUsers.isPresent()) {
            throw new UsersAlreadyExistsException(UserConstants.MESSAGE_ALREADY_EXISTS);
        }

        User user = UserMapper.mapToUsersDetailsRequestVo(checkUserVo);
        user.setRole(ROLE.USER);
        userRepository.save(user);
    }

    public String buildFileName(String email, String originalFileName) {
        return email + " : profile" + "/" + originalFileName;
    }

    public String uploadFile(String email, MultipartFile file) throws FileUploadException {

        if (file.isEmpty()) {
            return null;
        }

        String fileName = buildFileName(email, file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentDisposition("inline");

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new EncryptedPutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
//            throw new FileUploadFailException(IssueInquiriesConstants.fileUploadFailed, e);
            throw new FileUploadException(e.getMessage());
        }
        return String.valueOf(amazonS3Client.getUrl(bucketName, fileName));

    }

    @Override
    public void updateProfile(MultipartFile file) throws FileUploadException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));
        user.setImage(uploadFile(user.getEmail(), file));
        userRepository.save(user);
    }

    @Override
    public void updateNickName(String nickName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(UserConstants.MESSAGE_404));

        if(userRepository.findByNickName(user.getNickName())!=null){
            throw new UsersAlreadyExistsException(UserConstants.NICKNAME_ALREADY_EXISTS);
        }

        user.setNickName(nickName);
        userRepository.save(user);
    }
}
