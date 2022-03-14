package com.xxgame.admin.web.modules.customerservice.controller;

import com.xxgame.admin.web.modules.customerservice.controller.model.CsMessageDto;
import com.xxgame.admin.web.modules.customerservice.entity.CustomerService;
import com.xxgame.admin.web.modules.user.entity.SystemUser;
import com.xxgame.admin.web.modules.user.repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class CustomerServiceDtoConverter {

    @Autowired
    private SystemUserRepository systemUserRepository;

    /**
     * 转换成CsMessageDto列表
     * @param customerServices
     * @return
     */
    public List<CsMessageDto> toCsMessageDtos(List<CustomerService> customerServices) {
        List<CsMessageDto> dtos = new ArrayList<>();
        for (CustomerService customerService : customerServices) {
            dtos.add(this.toCsMessageDto(customerService));
        }

        return dtos;
    }

    /**
     * 转换成CsMessageDto
     * @param customerService
     * @return
     */
    public CsMessageDto toCsMessageDto(CustomerService customerService) {
        CsMessageDto dto = new CsMessageDto();
        dto.setId(customerService.getId() + "");
        dto.setServerId(customerService.getServerId());
        dto.setChannelId(customerService.getChannelId());
        dto.setStatus(customerService.getStatus());
        dto.setPlayerId(customerService.getPlayerId() + "");
        dto.setPlayerName(customerService.getPlayerName());
        dto.setLevel(customerService.getLevel());
        dto.setVipLevel(customerService.getVipLevel());
        dto.setIp(customerService.getIp());
        dto.setContext(customerService.getContext());

        if (customerService.getReplier() != 0) {
            SystemUser systemUser = systemUserRepository.getOne(customerService.getReplier());
            dto.setReplier(systemUser.getName());
        }
        dto.setReplyTitle(customerService.getReplyTitle());
        dto.setReply(customerService.getReply());
        dto.setCreateTime(customerService.getCreateTime());
        dto.setReplyTime(customerService.getReplyTime());

        return dto;
    }

}
