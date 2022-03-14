package com.xxgame.admin.web.modules.repair.service;

import com.xxgame.admin.web.modules.analysis.service.DataSummaryAnalyzer;
import com.xxgame.admin.web.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

/**
 * 登录留存
 */
@Service
public class LoginLossRepairtor {

    @Autowired
    private DataSummaryAnalyzer dataSummaryAnalyzer;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化
     */
    //@PostConstruct
    private void init() {
        IntStream.range(20200216, 20200230).forEach(daily -> {
            logger.info("开始修改登录留存数据，统计日期：{}", daily);
            dataSummaryAnalyzer.analyzeLoginLoss(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
        IntStream.range(20200301, 20200319).forEach(daily -> {
            logger.info("开始修改登录留存数据，统计日期：{}", daily);
            dataSummaryAnalyzer.analyzeLoginLoss(DateUtils.numberToDate(daily, DateUtils.PATTERN_YYYYMMDD));
        });
    }

}
