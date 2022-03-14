package com.xxgame.admin.web.modules.levelstatis.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgame.admin.web.modules.levelstatis.controller.model.LevelCount;
import com.xxgame.admin.web.modules.levelstatis.controller.model.LevelDistStatisDto;
import com.xxgame.admin.web.modules.levelstatis.controller.model.LevelLossStatisV1Dto;
import com.xxgame.admin.web.modules.levelstatis.entity.DailyLevelLossStatis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 */
@Service
public class LevelStatisDtoConverter {

    /**
     * JSONArray to Map
     * @param text
     * @return
     */
    public Map<Integer, Integer> toMap(String text) {
        Map<Integer, Integer> map = new HashMap<>();
        if (StringUtils.isBlank(text)) {
            return map;
        }

        Iterator<Object> it = JSONArray.parseArray(text).iterator();
        while (it.hasNext()) {
            JSONArray arry = (JSONArray) it.next();
            map.put(arry.getIntValue(0), arry.getIntValue(1));
        }

        return map;
    }

    /**
     * 转换成LevelDistStatisDto列表
     * @param rowMaps
     * @return
     */
    public List<LevelDistStatisDto> toLevelDistStatisDtos(List<Map<String, Object>> rowMaps) {
        if (rowMaps == null) {
            return new ArrayList<>(0);
        }
        
        List<LevelDistStatisDto> dtos = new ArrayList<>(rowMaps.size());
        for (Map<String, Object> row : rowMaps) {
            LevelDistStatisDto dto = new LevelDistStatisDto();
            dto.setDaily((int) row.get("daily"));
            dto.setServerId((int) row.get("server_id"));
            dto.setTotal((int) row.get("total"));

            List<LevelCount> levelCounts = new ArrayList<>();
            JSONObject jsonObject = JSONObject.parseObject((String) row.get("level_map"));
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                LevelCount count = new LevelCount();
                count.setLevel(Integer.parseInt(entry.getKey()));
                count.setCount((Integer) entry.getValue());
                levelCounts.add(count);
            }
            dto.setLevelCounts(levelCounts);

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 转换成LevelLossStatisV1Dto列表
     * @param lossStatiss
     * @return
     */
    public List<LevelLossStatisV1Dto> toLevelLossStatisV1Dtos(List<DailyLevelLossStatis> lossStatiss) {
        if (lossStatiss == null || lossStatiss.isEmpty()) {
             return new ArrayList<>(0);
        }

        List<LevelLossStatisV1Dto> list = new LinkedList<>();
        for (DailyLevelLossStatis lossStatis : lossStatiss) {
            list.add(this.toLevelLossStatisV1Dto(lossStatis));
        }

        return list;
    }

    /**
     * 转换成LevelLossStatisV1Dto
     * @param lossStatis
     * @return
     */
    public LevelLossStatisV1Dto toLevelLossStatisV1Dto(DailyLevelLossStatis lossStatis) {
        LevelLossStatisV1Dto dto = new LevelLossStatisV1Dto();
        dto.setServerId(lossStatis.getServerId());
        dto.setDaily(lossStatis.getDaily());
        dto.setLevel(lossStatis.getLevel());
        dto.setCount(lossStatis.getCount());
        dto.setLevelCount(lossStatis.getLevelCount());
        dto.setLoss24(lossStatis.getLoss24());
        dto.setLoss72(lossStatis.getLoss72());

        return dto;
    }

}
