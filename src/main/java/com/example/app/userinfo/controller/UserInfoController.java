package com.example.app.userinfo.controller;

import com.example.app.userinfo.dto.SignUpRequestDto;
import com.example.app.userinfo.dto.UpdateUserInfoRequestDto;
import com.example.app.userinfo.dto.UserInfoResponseDto;
import com.example.app.userinfo.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping
    public ResponseEntity<UserInfoResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){
        UserInfoResponseDto userInfoResponseDto =
                userInfoService.signUp(
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );
        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserInfoResponseDto>> findAllUsers(){
        List<UserInfoResponseDto> userInfoResponseDtos = userInfoService.findAllUsers();
        return new ResponseEntity<>(userInfoResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponseDto> findUserInfoById(@PathVariable Long id){
        UserInfoResponseDto userInfoResponseDto = userInfoService.findUserInfoById(id);
        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserInfoResponseDto> updateUserInfoById(
            @PathVariable Long id, @RequestBody UpdateUserInfoRequestDto requestDto
    ){
        UserInfoResponseDto userInfoResponseDto =
        userInfoService.updateUserInfoById(
                id, requestDto.getUsername(), requestDto.getOldPassword(), requestDto.getNewPassword()
        );
        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserInfo(@PathVariable Long id){
        userInfoService.deleteUserInfo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
