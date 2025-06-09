package com.local.crmx.sale.service;

import java.io.File;

public interface {class_service} {
    void addData({class_name} param);
    void updateData({class_name} param);
    void deleteData(List<{class_name}> param);

    void importData(File file);

    void batchUpdate(List<{class_name}> params);

}
