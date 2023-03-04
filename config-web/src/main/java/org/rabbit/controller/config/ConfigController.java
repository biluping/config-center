package org.rabbit.controller.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rabbit.controller.config.req.ConfigCreateReq;
import org.rabbit.controller.config.req.ConfigQueryHistoryReq;
import org.rabbit.controller.config.req.ConfigQueryReq;
import org.rabbit.controller.config.vo.ConfigVo;
import org.rabbit.convert.ConfigConvert;
import org.rabbit.service.ConfigService;
import org.rabbit.vo.BasicResultVO;
import org.rabbit.vo.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.rabbit.vo.BasicResultVO.success;

@Tag(name = "配置")
@Validated
@RestController
@RequestMapping("config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @Operation(summary = "新增配置")
    @PostMapping("create")
    public BasicResultVO<Boolean> createConfig(@Valid @RequestBody ConfigCreateReq req){
        return success(configService.createConfig(req));
    }

    @Operation(summary = "查询配置")
    @PostMapping("page")
    public BasicResultVO<PageResult<ConfigVo>> getConfigPage(@Valid @RequestBody ConfigQueryReq req){
        return success(ConfigConvert.INSTANCE.toPage(configService.getConfigPage(req)));
    }

    @Operation(summary = "查询 key 的历史发布版本")
    @PostMapping("versions")
    public BasicResultVO<List<ConfigVo>> getHistoryConfig(@Valid @RequestBody ConfigQueryHistoryReq req){
        return success(ConfigConvert.INSTANCE.toVo(configService.getHistoryConfig(req)));
    }

    @Operation(summary = "删除配置")
    @DeleteMapping("delete")
    public BasicResultVO<Boolean> deleteConfig(@NotNull(message = "配置id不能为空") @RequestParam Long configId){
        return success(configService.deleteConfig(configId));
    }

}
