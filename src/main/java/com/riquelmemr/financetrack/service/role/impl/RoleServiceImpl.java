package com.riquelmemr.financetrack.service.role.impl;

import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.RoleModel;
import com.riquelmemr.financetrack.repository.RoleRepository;
import com.riquelmemr.financetrack.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleModel findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new ModelNotFoundException("Role with " + name + " not found.")
        );
    }
}
