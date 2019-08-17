package com.itheima.health.service;

import java.util.Map;

public interface ReportService {

    Map<String,Object> getBusinessReportData();

    Map<String,Object> getMemberReportByDate(Map map) throws Exception;
}
