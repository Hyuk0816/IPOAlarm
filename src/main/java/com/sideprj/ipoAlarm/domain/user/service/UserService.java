package com.sideprj.ipoAlarm.domain.user.service;

import com.sideprj.ipoAlarm.domain.user.vo.UserDetailsRequestVo;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void createUsers(UserDetailsRequestVo userDetailsRequestVo);

    void updateProfile(MultipartFile file) throws FileUploadException;

    void updateNickName(String nickName);
}
