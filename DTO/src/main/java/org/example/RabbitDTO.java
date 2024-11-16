package org.example;

import lombok.Data;

@Data
public class RabbitDTO {
    private String accessToken;
    private String url;
    private String userEmail;
}