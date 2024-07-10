package com.other.print;


import cn.hutool.core.util.IdUtil;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.dy.components.logs.error.spring.GlobalErrorInfoDynamic;
import com.other.print.vo.ContractPO;
import com.util.CommonUtil;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.exception.GlobalErrorInfoEnum;
import com.yd.utils.common.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Component
public class PrintController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/addPrint")
    public CIPResponseMsg addPrint(ContractPO po) throws Exception{
        CIPResponseMsg success = CommonUtil.success();
        po.setSerial_no(IdUtil.simpleUUID());
        po.setCreate_time(new Timestamp(System.currentTimeMillis()));
        SimpleJdbcInsert insertActor = (new SimpleJdbcInsert(this.jdbcTemplate)).withTableName("crm_contract_log");
        try {
            insertActor.execute(new BeanPropertySqlParameterSource(po));
        } catch (DataAccessException var4) {
            throw new GlobalErrorInfoException(var4, new GlobalErrorInfoDynamic("16101703", String.format(GlobalErrorInfoEnum.FOUNDATION_FOUNDATION__16101015.getMessage()), GlobalErrorInfoEnum.FOUNDATION_FOUNDATION__16101015.getSolutionList()));
        }
        success.data=po.getSerial_no();
        return success;
    }
    @RequestMapping(value = "/getPrint")
    public CIPResponseMsg getPrint(ContractPO po) throws Exception{
        CIPResponseMsg success = CommonUtil.success();
        String sql="select * from crm_contract_log where serial_no=?";
        List<ContractPO> list = jdbcTemplate.query(sql, new Object[]{po.getSerial_no()}, BeanPropertyRowMapper.newInstance(ContractPO.class));
        if(CollectionUtil.isNotEmpty(list)){
            success.data=list.get(0);
        }
        return success;
    }


}
