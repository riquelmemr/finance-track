package com.riquelmemr.financetrack.service.role;

import com.riquelmemr.financetrack.model.RoleModel;

public interface RoleService {
    RoleModel findByName(String name);
}
