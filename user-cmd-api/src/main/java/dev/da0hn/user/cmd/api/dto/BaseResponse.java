package dev.da0hn.user.cmd.api.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BaseResponse {

  String message;

}
