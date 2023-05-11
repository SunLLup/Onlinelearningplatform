package com.lyj.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class lisensetest extends AnalysisEventListener<User> {

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println("读的数据为"+user);
    }

    @Override
    public void invokeHead(java.util.Map<java.lang.Integer,com.alibaba.excel.metadata.CellData> headMap, com.alibaba.excel.context.AnalysisContext context) {
        System.out.println("表头为"+headMap);
        /* compiled code */ }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

