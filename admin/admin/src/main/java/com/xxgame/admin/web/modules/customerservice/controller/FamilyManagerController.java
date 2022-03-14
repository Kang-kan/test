package com.xxgame.admin.web.modules.customerservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.customerservice.controller.model.FamilyInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("familyManager")
@Api(tags = "仙盟管理-900")
public class FamilyManagerController extends BaseController {

    @Autowired
    private GmClient gmClient;

    @RequiresPermissions("listFamily:query")
    @GetMapping(value = "listFamily")
    @ApiOperation(value = "仙盟列表-903")
    @ServerRight(argsIndex = 0)
    public Result<PageDto<FamilyInfoDto>> listFamily(@RequestParam int serverId, @RequestParam int pageNo, @RequestParam int pageSize) {
        int[] queryArgs = toQueryArgs(pageNo, pageSize);
        String result = gmClient.listFamilys(serverId, queryArgs[0], queryArgs[1]);

        if (StringUtils.isBlank(result)) {
            return Result.success(PageDto.empty(pageNo, pageSize));
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray contents = jsonObject.getJSONArray("contents");
        int totalCount = jsonObject.getIntValue("totalCount");
        int totalPage = (int) Math.ceil(totalCount * 1.0F / pageSize);

        List<FamilyInfoDto> dtos = JSON.parseObject(contents.toJSONString(), new TypeReference<List<FamilyInfoDto>>(){});

        PageDto pageDto = new PageDto();
        pageDto.setContents(dtos);
        pageDto.setPageSize(pageSize);
        pageDto.setPageNo(pageNo);
        pageDto.setTotalPage(totalPage);
        pageDto.setTotalCount(totalCount);

        return Result.success(pageDto);
    }

    @RequiresPermissions("updateFamilyNotice:query")
    @GetMapping(value = "updateFamilyNotice")
    @ApiOperation(value = "修改仙盟公告-904")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "notice", value = "最长180个字符")
    })
    @ServerRight(argsIndex = 0)
    public Result<String> updateFamilyNotice(@RequestParam int serverId, @RequestParam String familyId, @RequestParam String notice) {
        if (StringUtils.isBlank(familyId)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        if (notice == null) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        if (notice.length() > 180) {
            return Result.error(ResultCode.PARAM_MAX_LENGTH, "参数内容过长");
        }

        String result = gmClient.updateFamilyNotice(serverId, familyId, notice);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或操作失败");
        }
        if (result.equals("true")) {
            return Result.success(result);
        }

        return Result.error(ResultCode.FAIL, result);
    }

}
