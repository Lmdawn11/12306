package com.ming.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.ming.train.member.domain.Member;
import com.ming.train.member.domain.MemberExample;
import com.ming.train.member.exception.BusinessExceptionEnum;
import com.ming.train.member.exception.BussinessException;
import com.ming.train.member.mapper.MemberMapper;
import com.ming.train.member.req.MemberRegisterReq;
import com.ming.train.member.util.SnowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;

    public int count(){
        return (int) memberMapper.countByExample(null);
    }
    public long register(MemberRegisterReq req){
        String mobile = req.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> lists = memberMapper.selectByExample(memberExample);
        if (CollUtil.isNotEmpty(lists)){
//            return lists.get(0).getId();
            throw new BussinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

}
