package com.ripan.production.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactInformation {

    private String email;
    private String mobile;
    private String facebook;
}
