package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.RoleBean;
import in.cdac.university.usm.entity.UmmtRoleMst;
import in.cdac.university.usm.entity.UmstModuleMst;
import in.cdac.university.usm.repository.ModuleRepository;
import in.cdac.university.usm.repository.RoleRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private Language language;

    public List<RoleBean> getAllRoles() {
        return BeanUtils.copyListProperties(roleRepository.findByGblIsvalidOrderByGstrRoleNameAsc(1), RoleBean.class);
    }

    public List<RoleBean> getRolesListPage(Integer isValid) {
        return roleRepository.findByGblIsvalidOrderByGstrRoleNameAsc(isValid)
                .stream()
                .map(roleEntity -> {
                    RoleBean roleBean = BeanUtils.copyProperties(roleEntity, RoleBean.class);
                    roleBean.setModuleName(roleEntity.getModule().getGstrModuleName());
                    return roleBean;
                })
                .collect(Collectors.toList());
    }

    public ServiceResponse saveRole(RoleBean roleBean) {
        // Check for duplicate Role name
        Optional<UmmtRoleMst> ummtRoleMst = roleRepository.findByGstrRoleNameIgnoreCaseAndGblIsvalidNot(roleBean.getGstrRoleName(), 0);
        if (ummtRoleMst.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Role Name", roleBean.getGstrRoleName()));
        }

        // Check for Module Name
        Optional<UmstModuleMst> moduleMst = moduleRepository.findById(roleBean.getGnumModuleId());
        if (moduleMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Module", roleBean.getGnumModuleId()));
        }

        UmmtRoleMst roleMst = BeanUtils.copyProperties(roleBean, UmmtRoleMst.class);
        roleMst.setModule(moduleMst.get());

        Integer roleId = roleRepository.save(roleMst).getGnumRoleId();
        int status = 0;
        String message;
        if (roleId != null) {
            message = language.saveSuccess("Role");
            status = 1;
        } else {
            message = language.saveError("Role");
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse update(RoleBean roleBean) {
        if (roleBean.getGnumRoleId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Role Id"));
        }

        // Check if Role exists
        Optional<UmmtRoleMst> roleMstOptional = roleRepository.findByGnumRoleIdAndGblIsvalidNot(roleBean.getGnumRoleId(), 0);
        if (roleMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Role", roleBean.getGnumRoleId()));
        }

        // Duplicate check
        Optional<UmmtRoleMst> roleMstDuplicateOptional = roleRepository.findByGstrRoleNameIgnoreCaseAndGnumRoleIdNotAndGblIsvalidNot(roleBean.getGstrRoleName(), roleBean.getGnumRoleId(), 0);
        if (roleMstDuplicateOptional.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Role", roleBean.getGstrRoleName()));
        }

        // Check for Module Name
        Optional<UmstModuleMst> moduleMst = moduleRepository.findById(roleBean.getGnumModuleId());
        if (moduleMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Module", roleBean.getGnumModuleId()));
        }

        UmmtRoleMst roleMst = BeanUtils.copyProperties(roleBean, UmmtRoleMst.class);
        roleMst.setModule(moduleMst.get());
        Integer roleId = roleRepository.save(roleMst).getGnumRoleId();
        int status = 0;
        String message;
        if (roleId != null) {
            message = language.updateSuccess("Role");
            status = 1;
        } else {
            message = language.updateError("Role");
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse delete(RoleBean roleBean) {
        if (roleBean.getIdsToDelete() == null || roleBean.getIdsToDelete().length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Role Id"));
        }

        List<Integer> roleIdsToDelete = Stream.of(roleBean.getIdsToDelete())
                .map(Integer::valueOf)
                .toList();

        long noOfReservedRoles = roleIdsToDelete.stream().filter(roleid -> roleid <= 0).count();
        if (noOfReservedRoles > 0L) {
            return ServiceResponse.errorResponse("Reserved Roles cannot be deleted");
        }

        // Check if Role exists
        List<UmmtRoleMst> roleMstList = roleRepository.findAllByGnumRoleIdInAndGblIsvalidNot(roleIdsToDelete, 0);
        if (roleMstList.isEmpty() || roleMstList.size() != roleBean.getIdsToDelete().length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Role", Arrays.toString(roleBean.getIdsToDelete())));
        }

        for (UmmtRoleMst roleMst : roleMstList) {
            roleMst.setGblIsvalid(0);
            roleMst.setGnumEntryBy(roleBean.getGnumEntryBy());
        }
        roleRepository.saveAll(roleMstList);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Role"))
                .build();
    }

    public ServiceResponse getRole(Integer roleId) {
        if (roleId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Role Id"));
        }

        Optional<UmmtRoleMst> roleMstOptional = roleRepository.findByGnumRoleIdAndGblIsvalidNot(roleId, 0);
        if (roleMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Role", roleId));
        }
        RoleBean roleBean = BeanUtils.copyProperties(roleMstOptional.get(), RoleBean.class);
        roleBean.setGnumModuleId(roleMstOptional.get().getModule().getGnumModuleId());

        return ServiceResponse.successObject(roleBean);
    }

    public List<RoleBean> getRoleComboForModule(Integer moduleId) {
        if (moduleId == null) {
            return new ArrayList<>();
        }

        List<UmmtRoleMst> roleList = roleRepository.findByModuleGnumModuleIdAndGblIsvalid(moduleId, 1);
        return BeanUtils.copyListProperties(roleList, RoleBean.class);
    }
}
