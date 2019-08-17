package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author mao
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
    //查询指定年龄段的会员人数
    List<Map<String,Object>> findMemberReportByAge();
}
