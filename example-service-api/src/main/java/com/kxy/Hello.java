package com.kxy;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Hello implements Serializable {
    private String message;
    private String desc;
}
