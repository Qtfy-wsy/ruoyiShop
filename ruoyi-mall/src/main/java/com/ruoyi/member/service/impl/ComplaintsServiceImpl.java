package com.ruoyi.member.service.impl;

import com.ruoyi.member.domain.Complaints;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.mapper.ComplaintsMapper;
import com.ruoyi.member.service.ComplaintsService;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created By Caizize on 2018.3.21
 * <p>
 * 平台投诉服务实现类
 */
@Service
public class ComplaintsServiceImpl implements ComplaintsService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(ComplaintsServiceImpl.class);

    /**
     * 注入平台投诉数据库接口
     */
    @Autowired
    private ComplaintsMapper complaintsMapper;


    /**
     * 注入用户信息业务层接口
     */
    @Autowired
    private IUmsMemberService customerService;

    @Override
    public int addComplaints(Complaints complaints) {

        logger.debug("addComplaints and complaints", complaints);

        if (ObjectUtils.isEmpty(complaints)) {
            logger.error("addComplaints fail due to complaints is empty");
            return 0;
        }

        return complaintsMapper.addComplaints(complaints);
    }

    @Override
    public int updateComplaintsReplay(Complaints complaints) {

        logger.debug("updateComplaintsReplay and complaints", complaints);

        if (ObjectUtils.isEmpty(complaints)) {
            logger.error("updateComplaintsReplay fail due to complaints is empty");
            return 0;
        }

        return complaintsMapper.updateComplaintsReplay(complaints);
    }

    @Override
    public PageHelper<Complaints> queryComplaints(PageHelper<Complaints> pageHelper, long customerId, String status) {

        logger.debug("queryComplaints and pageHelper :{} \r\n customerId :{} \r\n status :{}", pageHelper, customerId, status);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("status", status);


        //查询并返回
        return pageHelper.setListDates(complaintsMapper.queryComplaints(pageHelper.getQueryParams(params, complaintsMapper.queryComplaintsCount(params))).stream().map(complaints -> complaints.addUsername(
                Optional.ofNullable(customerService.queryCustomerWithNoPasswordById(complaints.getCustomerId())).orElse(new UmsMember().addUsername("user does not exist ")).getUsername())).collect(Collectors.toList()));
    }
    @Override
    public Complaints queryComplaintsById(long id){
        return complaintsMapper.queryComplaintsById(id);
    }


}
