package com.sideprj.ipoAlarm.domain.user.service;

import com.sideprj.ipoAlarm.domain.user.entity.User;
import com.sideprj.ipoAlarm.domain.user.util.UserInfo;
import com.sideprj.ipoAlarm.domain.user.vo.UserDetailsRequestVo;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void createUsers(UserDetailsRequestVo userDetailsRequestVo);

    void updateProfile(MultipartFile file, @UserInfo User user) throws FileUploadException;

    void updateNickName(String nickName,@UserInfo User user);

    String nickNameCheck(String nickName,@UserInfo User user);

}
