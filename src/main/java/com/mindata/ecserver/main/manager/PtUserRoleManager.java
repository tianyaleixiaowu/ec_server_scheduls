package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtUserRoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/31.
 */
@Service
public class PtUserRoleManager {
    @Resource
    private PtUserRoleRepository ptUserRoleRepository;

    /**
     * 查询某个role的集合
     */
    public List<PtUserRole> findByRoleId(Long roleId) {
        return ptUserRoleRepository.findByRoleId(roleId);
    }
}
