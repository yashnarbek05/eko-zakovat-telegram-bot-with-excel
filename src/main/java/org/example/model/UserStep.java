package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStep {
    private String chatId;
    private String stepName;
    private String stepLang;
    private boolean registered;

}
