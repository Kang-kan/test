package com.xxgame.admin.web.modules.consumestatis.service;

import com.xxgame.admin.web.modules.consumestatis.entity.GoldConsumeStatis;
import com.xxgame.admin.web.modules.consumestatis.entity.MarketStatis;
import com.xxgame.admin.web.modules.consumestatis.repository.ConsumeStatisRepository;
import com.xxgame.admin.web.modules.consumestatis.repository.GoldConsumeStatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 商城消耗统计
 */
@Service
public class ConsumeStatisServiceImpl implements ConsumeStatisService {

    @Autowired
    private GoldConsumeStatisRepository goldConsumeStatisRepository;
    @Autowired
    private ConsumeStatisRepository consumeStatisRepository;

    @Override
    public Page<GoldConsumeStatis> findFunctionStatis(int startTime, int endTime, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return goldConsumeStatisRepository.findFunctionStatis(startTime, endTime, pageRequest);
    }

    @Override
    public Page<GoldConsumeStatis> findFunctionStatis(int startTime, int endTime, int serverId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return goldConsumeStatisRepository.findFunctionStatis(startTime, endTime, serverId, pageRequest);
    }

    @Override
    public Page<MarketStatis> findMarketStatis(int startTime, int endTime, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return consumeStatisRepository.findMarketStatis(startTime, endTime, pageRequest);
    }

    @Override
    public Page<MarketStatis> findMarketStatis(int startTime, int endTime, int serverId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return consumeStatisRepository.findMarketStatis(startTime, endTime, serverId, pageRequest);
    }

}
