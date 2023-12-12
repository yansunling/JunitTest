package com.squirrel.tmsp_hand_book_doc.engine;

import com.squirrel.AbstractStateContext;
import com.squirrel.AbstractStateMachineEngine;
import com.squirrel.constants.TMSP_hand_book_docPO;
import com.squirrel.mapper.TMSP_hand_book_docMapper;
import com.squirrel.tmsp_hand_book_doc.desc.BookDocStatusDesc;
import com.squirrel.tmsp_hand_book_doc.event.BookDocStatusEvent;
import com.squirrel.tmsp_hand_book_doc.machine.BookDocStatusMachine;
import com.yd.common.cache.Cacheable;

import com.yd.utils.common.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 约车单接货状态机调用引擎类 设计生成 请勿修改！
 */
@Service
public class BookDocStatusEngine extends AbstractStateMachineEngine<BookDocStatusMachine,String, BookDocStatusEvent, AbstractStateContext, TMSP_hand_book_docPO, TMSP_hand_book_docMapper> {
    /**
     * 状态检查方法(不查数据库)
     * <p>
     * 检查keys对应单据记录的当前状态是否可以被此调用入口调用，有调用栈限制
     *
     * @param stateEnum
     * @return
     */
    public boolean checkStatusNoDb(Cacheable stateEnum) {
        return super.checkCanAcceptNoDb(null, stateEnum);
    }
    /**
     * 状态检查方法
     * <p>
     * 检查keys对应单据记录的当前状态是否可以被此调用入口调用，有调用栈限制
     *
     * @param keys
     * @return
     */
    public boolean checkStatus(String... keys) {
        return super.checkCanAccept(null, "", keys);
    }

    /**
     * 不查数据库
     * 状态检查批量方法 所有单据状态都为正常 才返回true，有调用栈限制
     */
    public boolean batchCheckStatusNoDb(List<Cacheable> cEnumList) {
        boolean toReturn = true;
        if (CollectionUtil.isNotEmpty(cEnumList)) {
            for (Cacheable cEnum : cEnumList) {
                toReturn = toReturn && checkStatusNoDb(cEnum);
            }
        }
        return toReturn;
    }

    /**
     * 状态检查批量方法 所有单据状态都为正常 才返回true，有调用栈限制
     */
    public boolean batchCheckStatus(List<String[]> keyList) {
        boolean toReturn = true;
        if (CollectionUtil.isNotEmpty(keyList)) {
            for (String[] key : keyList) {
                toReturn = toReturn && checkStatus(key);
            }
        }
        return toReturn;
    }
    /**
     * 不查数据库
     * 状态检查并返回详情但是不持久化状态，有调用栈限制
     * @param stateEnum
     * @return
     */
    public BookDocStatusDesc checkAndReturnNoDb(Cacheable stateEnum) {
        return (BookDocStatusDesc) super.checkAndReturnNoDb(null, new BookDocStatusDesc(), stateEnum);
    }
    /**
    * 状态检查并返回详情但是不持久化状态，有调用栈限制
    * @param keys
    * @return
    */
    public BookDocStatusDesc checkAndReturn(String... keys) {
        return (BookDocStatusDesc) super.checkAndReturn(null, new BookDocStatusDesc(),"doc_status", keys);
    }
    /**
     * 不查数据库
     * 状态检查并返回详情但是不持久化状态（批量方法），有调用栈限制
     * @param cEnumList
     * @return
     */
    public List<BookDocStatusDesc> batchCheckAndReturnNoDb(List<Cacheable> cEnumList) {
        if (CollectionUtil.isEmpty(cEnumList)) {
            return null;
        }
        List<BookDocStatusDesc> toReturn = new ArrayList<>();
        cEnumList.forEach(cEnum -> {
            BookDocStatusDesc desc = checkAndReturnNoDb(cEnum);
            toReturn.add(desc);
        });
        return toReturn;
    }
    /**
    * 状态检查并返回详情但是不持久化状态（批量方法），有调用栈限制
    * @param keyList
    * @return
    */
    public List<BookDocStatusDesc> batchCheckAndReturn(List<String[]> keyList) {
        if (CollectionUtil.isEmpty(keyList)) {
            return null;
        }
        List<BookDocStatusDesc> toReturn = new ArrayList<>();
        keyList.forEach(key -> {
           BookDocStatusDesc desc = checkAndReturn(key);
            toReturn.add(desc);
        });
        return toReturn;
    }

    /**
     * 状态机迁移状态主方法 返回迁移后状态及初始状态
     * <p>
     * 针对keys对应单据记录的当前状态进行状态变更并做持久化操作，有调用栈限制，
     * 只能被event事件枚举指定的方法入口调用，
     * 会按方法调用栈动态取值然后校验事件枚举指定的类和方法入口
     *
     * @param keys
     * @return BookDocStatusDesc
     */
    public BookDocStatusDesc checkAndSaveStatus(String... keys) {
        return (BookDocStatusDesc) super.fire(null, new BookDocStatusDesc(),"doc_status", keys);
    }

    /**
     * 状态机迁移状态主方法 返回迁移后状态及初始状态 批量方法
     * <p>
     * 针对keys对应单据记录的当前状态进行状态变更并做持久化操作，有调用栈限制，
     * 只能被event事件枚举指定的方法入口调用，
     * 会按方法调用栈动态取值然后校验事件枚举指定的类和方法入口
     *
     * @param keyList
     * @return List<BookDocStatusDesc>
     */
    public List<BookDocStatusDesc> batchCheckAndSaveStatus(List<String[]> keyList) {
        if (CollectionUtil.isEmpty(keyList)) {
            return null;
        }
        List<BookDocStatusDesc> toReturn = new ArrayList<>();
        keyList.forEach(key -> {
            BookDocStatusDesc desc = checkAndSaveStatus(key);
            toReturn.add(desc);
        });
        return toReturn;
    }


}
