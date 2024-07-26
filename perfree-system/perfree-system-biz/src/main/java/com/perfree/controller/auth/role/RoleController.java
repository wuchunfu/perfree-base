package com.perfree.controller.auth.role;


import com.perfree.commons.common.CommonResult;
import com.perfree.commons.common.PageResult;
import com.perfree.controller.auth.role.vo.RoleAddOrUpdateReqVO;
import com.perfree.controller.auth.role.vo.RoleMenuReqVO;
import com.perfree.controller.auth.role.vo.RolePageReqVO;
import com.perfree.controller.auth.role.vo.RoleRespVO;
import com.perfree.convert.role.RoleConvert;
import com.perfree.model.Role;
import com.perfree.model.RoleMenu;
import com.perfree.service.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.perfree.commons.common.CommonResult.success;

/**
 * @description 角色
 * @author Perfree
 * @version 1.0.0
 * @create 2023/1/16 10:16
 **/
@RestController
@Tag(name = "角色相关接口")
@RequestMapping("api/auth/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/page")
    @Operation(summary = "角色分页列表")
    public CommonResult<PageResult<RoleRespVO>> page(@RequestBody RolePageReqVO pageVO) {
        PageResult<Role> rolePageResult = roleService.rolePage(pageVO);
        return success(RoleConvert.INSTANCE.convertPageResultVO(rolePageResult));
    }

    @GetMapping("/getRoleMenus")
    @Operation(summary = "获取角色所拥有的菜单列表")
    public CommonResult<List<String>> getRoleMenus(@RequestParam(value = "id") Integer id) {
        List<RoleMenu> roleMenus = roleService.getRoleMenus(id);
        List<String> result = new ArrayList<>();
        for (RoleMenu roleMenu : roleMenus) {
            result.add(roleMenu.getMenuId());
        }
        return success(result);
    }

    @PostMapping("/assignRoleMenu")
    @Operation(summary = "设置角色菜单权限")
    public CommonResult<Boolean> assignRoleMenu(@RequestBody @Valid RoleMenuReqVO roleMenuReqVO) {
        return success(roleService.assignRoleMenu(roleMenuReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获取角色")
    public CommonResult<RoleRespVO> get(@RequestParam(value = "id") Integer id) {
        return success(RoleConvert.INSTANCE.convertRespVO(roleService.get(id)));
    }

    @GetMapping("/listAll")
    @Operation(summary = "获取所有角色")
    public CommonResult<List<RoleRespVO>> listAll() {
        return success(RoleConvert.INSTANCE.convertRespListVO(roleService.list()));
    }

    @PostMapping("/addOrUpdate")
    @Operation(summary = "添加或更新")
    public CommonResult<RoleRespVO> addOrUpdate(@RequestBody @Valid RoleAddOrUpdateReqVO roleAddOrUpdateReqVO) {
        return success(RoleConvert.INSTANCE.convertRespVO(roleService.addOrUpdate(roleAddOrUpdateReqVO)));
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除角色")
    public CommonResult<Boolean> del(@RequestParam(value = "id") Integer id) {
        return success(roleService.del(id));
    }
}
