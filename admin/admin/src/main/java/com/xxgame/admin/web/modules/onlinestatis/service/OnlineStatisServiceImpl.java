package com.xxgame.admin.web.modules.onlinestatis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyChannelOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.DailyOnlineStatis;
import com.xxgame.admin.web.modules.onlinestatis.entity.OnlineStatisDist;
import com.xxgame.admin.web.modules.onlinestatis.repository.DailyChannelOnlineStatisRepository;
import com.xxgame.admin.web.modules.onlinestatis.repository.DailyOnlineStatisRepository;
import com.xxgame.admin.web.modules.onlinestatis.repository.OnlineStatisDistRepository;
import com.xxgame.admin.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 在线统计
 */
@Service
public class OnlineStatisServiceImpl implements OnlineStatisService {

    @Autowired
    private OnlineStatisDistRepository onlineStatisDistRepository;
    @Autowired
    private DailyOnlineStatisRepository dailyOnlineStatisRepository;
    @Autowired
    private DailyChannelOnlineStatisRepository dailyChannelOnlineStatisRepository;

    @Override
    public Page<OnlineStatisDist> findOnlineDist(int startTime, int endTime, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return onlineStatisDistRepository.findOnlineDist(startTime, endTime, pageRequest);
    }

    @Override
    public Page<OnlineStatisDist> findOnlineDist(int startTime, int endTime, int serverId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return onlineStatisDistRepository.findOnlineDist(startTime, endTime, serverId, pageRequest);
    }

    @Override
    public Page<DailyOnlineStatis> findOnlineStatis(int startTime, int endTime, List<Integer> serverIds, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyOnlineStatisRepository.findOnlineStatis(startTime, endTime, serverIds, pageRequest);
    }

    @Override
    public Page<DailyChannelOnlineStatis> findOnlineStatis(int startTime, int endTime, int serverId, String channelId, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyChannelOnlineStatisRepository.findOnlineStatis(startTime, endTime, serverId, channelId, pageRequest);
    }

    @Override
    public List<DailyChannelOnlineStatis> findOnlineStatis(Date startTime, Date endTime) {
        String startDaily = DateUtils.date2String(startTime, DateUtils.PATTERN_YYYYMMDD);
        String endDaily = DateUtils.date2String(endTime, DateUtils.PATTERN_YYYYMMDD);
        return dailyChannelOnlineStatisRepository.findOnlineStatis(Integer.parseInt(startDaily), Integer.parseInt(endDaily));
    }

    @Override
    public Page<DailyChannelOnlineStatis> findChannelOnlineStatis(int startTime, int endTime, List<Integer> serverIds, List<String> channelIds, int pageNo, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "daily", "createTime");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return dailyChannelOnlineStatisRepository.findOnlineStatis(startTime, endTime, serverIds, channelIds, pageRequest);
    }

    @Override
    public int getMaxOnline(int daily, int serverId) {
        String id = String.format("%s%s", daily, serverId);
        Optional<DailyOnlineStatis> optional = dailyOnlineStatisRepository.findById(id);
        if (!optional.isPresent()) {
            return 0;
        }

        List<Integer> onlinCounts = JSON.parseObject(optional.get().getStatis(), new TypeReference<List<Integer>>(){});
        if (onlinCounts == null || onlinCounts.isEmpty()) {
            return 0;
        }

        return onlinCounts.stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    @Override
    public int[] calOnlineStatis(DailyChannelOnlineStatis onlineStatis) {
        if (onlineStatis == null) {
            return new int[]{ 0, 0, 0 };
        }

        List<Integer> statis = JSON.parseObject(onlineStatis.getStatis(), new TypeReference<List<Integer>>(){});
        if (statis == null || statis.isEmpty()) {
            return new int[]{ 0, 0, 0 };
        }
        int min = statis.stream().mapToInt(Integer::intValue).min().getAsInt();
        int max = statis.stream().mapToInt(Integer::intValue).max().getAsInt();
        double avg = statis.stream().mapToInt(Integer::intValue).average().getAsDouble();

        return new int[]{ min, max, (int) Math.ceil(avg) };
    }

}
