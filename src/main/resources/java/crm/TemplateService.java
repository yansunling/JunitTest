package com.local.crmx.sale.service;

import com.yd.common.runtime.CIPRuntimeOperator;


import java.io.File;
import java.util.List;

public interface {class_service} {
    void addData({class_name} param);
    void updateData({class_name} param);
    void deleteData(List<{class_name}> param);
    void updateStatus(List<String> param);
    void importData(File file, CIPRuntimeOperator operator);
}
