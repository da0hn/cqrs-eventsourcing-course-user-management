package dev.da0hn.user.cmd.api.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class RegisterUserResponse extends BaseResponse {

  private String id;

}
