package org.example.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String fullName;
    private String phoneNumber;
    private String tgLink;
}
