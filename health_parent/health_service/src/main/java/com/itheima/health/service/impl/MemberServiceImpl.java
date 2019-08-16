package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:55
 * @Version V1.0
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;


    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberDao.findMemberByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByRegTime(List<String> months) {
        List<Integer> memberCount = new ArrayList<Integer>();
        // 遍历集合
        for (String month : months) {
            // [2018-09, 2018-10, 2018-11, 2018-12, 2019-01, 2019-02, 2019-03, 2019-04, 2019-05, 2019-06, 2019-07, 2019-08]
            month = month + "-31";
            // 使用注册时间查询当前月之前的会员数量
            Integer count = memberDao.findMemberCountByRegTime(month);
            memberCount.add(count);
        }
        return memberCount;
    }
    //通过年龄算出人数占比
    @Override
    public Map<String, Object> findMemberReportByAge() {
        Map<String,Object> map=new HashMap<>();
        //封装指定年龄段及其人数
        List<Map<String,Object>> listAgeCount=memberDao.findMemberReportByAge();
        //封装指定年龄段
        List<String> listAge=new ArrayList<>();
        if(listAgeCount!=null){
            for (Map<String, Object> stringObjectMap : listAgeCount) {
                String name = (String) stringObjectMap.get("name");
                listAge.add(name);
            }
        }
        //添加到map中
        map.put("ages",listAge);
        map.put("agesCount",listAgeCount);
        return map;
    }
}
