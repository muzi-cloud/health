package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.springframework.stereotype.Repository;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/8/2 15:53
 * @Version V1.0
 */
@Repository
public interface MemberDao {

    Member findMemberByTelephone(String telephone);

    void add(Member member);

    // 使用注册时间查询当前月之前的会员数量
    Integer findMemberCountByRegTime(String month);

    // 当天新增会员数
    Integer findTodayNewMember(String reportDate);

    Integer findTotalMember();

    Integer findNewMemberAfterRegTime(String monthFirstDay);
}
