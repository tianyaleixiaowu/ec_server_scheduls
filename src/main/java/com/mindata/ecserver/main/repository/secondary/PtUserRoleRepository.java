package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtUserRoleRepository extends JpaRepository<PtUserRole, Integer> {
    List<PtUserRole> findByUserId(Integer userId);

    PtUserRole findByUserIdAndRoleId(Integer userId, Integer roleId);

    /**
     * 查询所有公司管理员的id
     * @param roleId
     * 公司管理员
     * @return
     * 公司管理员集合
     */
    List<PtUserRole> findByRoleId(Integer roleId);
}
