package com.riquelmemr.financetrack.dto.request;

import com.riquelmemr.financetrack.model.UserModel;
import lombok.Data;

@Data
public class CategoryFilterRequest {
    private UserModel user;
    private String code;
    private String name;
    private String description;
}
