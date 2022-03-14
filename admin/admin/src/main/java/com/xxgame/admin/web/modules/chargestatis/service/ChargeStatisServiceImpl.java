package com.xxgame.admin.web.modules.chargestatis.service;

import com.xxgame.admin.web.modules.chargestatis.entity.ChargeLossStatis;
import com.xxgame.admin.web.modules.chargestatis.repository.ChargeLossStatisRepository;
import com.xxgame.admin.web.modules.commons.GmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 充值统计
 */
@Service
public class ChargeStatisServiceImpl implements ChargeStatisService {

    @Autowired
    private GmClient gmClient;
    @Autowired
    private ChargeLossStatisRepository chargeLossStatisRepository;

    @Override
    public int queryLossCount(int serverId, String channelId, Date startDate, Date endDate) {
        Object[] args = new Object[]{ startDate.getTime(), endDate.getTime(), serverId, channelId };
        String result = gmClient.querySqlForGame(serverId, "PLAYER_LOSS_COUNT", 1, 0, 0, args);

        return Integer.parseInt(result);
    }

    @Override
    public Page<ChargeLossStatis> findLossStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return chargeLossStatisRepository.findChargeLoss(startTime, endTime, serverIds, channelIds, pageRequest);
    }

}
