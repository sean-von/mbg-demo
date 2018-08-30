package com.smikevon.easy.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.smikevon.easy.biz.service.DemoService;
import com.smikevon.easy.common.utils.IdWorker;
import com.smikevon.easy.common.utils.PageParam;
import com.smikevon.easy.mbg.mapper.OptLogMapper;
import com.smikevon.easy.model.entity.OptLog;
import com.smikevon.easy.model.entity.OptLogExample;
import com.smikevon.easy.model.enums.DeleteType;
import com.smikevon.easy.model.enums.OptType;

/**
 * Created by sean (smikevon@163.com) on 2018/8/20.
 */
@Service
public class DemoServiceImpl implements DemoService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DemoServiceImpl.class);
    @Autowired
    private OptLogMapper optLogMapper;

    @Override
    public void sayHello() {
        OptLog optLog = new OptLog();
        optLog.setId(IdWorker.nextId());
        optLog.setIp("");
        optLog.setItem(1L);
        optLog.setOptType(OptType.ADD);
        optLog.setKeywords("");
        optLog.setNewValue("new");
        optLog.setOldValue("old");
        optLog.setIsDelete(DeleteType.NOT_DELETED);
        optLogMapper.insert(optLog);
        log.info("hello I'm service class");
    }

    @Override
    @Transactional
    public void rollback() {
        sayHello();
        log.info("now one opt log is prepare to commit!");
        throw new RuntimeException("just roll back");
    }

    @Override
    @Cacheable(value = "demo.getLogByPage", key = "#pageParam.pageNum", condition = "#pageParam.pageSize > 3")
    public PageInfo<OptLog> getLogByPage(PageParam pageParam) {
        log.info("测试分页 {}, {}", pageParam);
        PageMethod.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<OptLog> optLogList = optLogMapper.selectByExample(null);
        return new PageInfo<>(optLogList);
    }

    @Override
    @CacheEvict(value = "demo.getLogByPage", allEntries = true)
    public Void clearPage() {
        log.info("clear page cache success !");
        return null;
    }

    @Override
    @Cacheable(value = "demo.optLog", key = "#logId")
    public OptLog getLog(Long logId) {
        return optLogMapper.selectByPrimaryKey(logId);
    }

    @Override
    public OptLog getById(Long logId) {
        return optLogMapper.getById(logId);
    }

    /**
     * 实验验证，并未产生脏读问题，即便在同一事物内，中间的插入操作也清除了 cache
     *
     * @return
     */
    @Override
    @Transactional
    public List<OptLog> getAll() {
        all();
        sayHello();
        return all();
    }

    private List<OptLog> all() {
        OptLogExample example = new OptLogExample();
        example.createCriteria().andIsDeleteEqualTo(DeleteType.NOT_DELETED);
        List<OptLog> list = optLogMapper.selectByExample(example);
        log.info("数据大小为 {} ", list.size());
        return list;
    }

}
