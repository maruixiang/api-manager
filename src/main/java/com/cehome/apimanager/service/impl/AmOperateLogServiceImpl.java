package com.cehome.apimanager.service.impl;

import com.cehome.apimanager.common.Page;
import com.cehome.apimanager.dao.AmOperateLogDao;
import com.cehome.apimanager.model.dto.AmOperateLogQueryReqDto;
import com.cehome.apimanager.model.dto.AmOperateLogReqDto;
import com.cehome.apimanager.model.dto.AmOperateLogResDto;
import com.cehome.apimanager.model.po.AmOperateLog;
import com.cehome.apimanager.service.IAmOperateLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AmOperateLogServiceImpl implements IAmOperateLogService {
    @Autowired
    private AmOperateLogDao operateLogDao;

    @Override
    public void add(AmOperateLogReqDto dto) {
        dto.setOperateTime(new Date());
        operateLogDao.add(dto);
    }

    @Override
    public void update(AmOperateLogReqDto dto) {
        operateLogDao.update(dto);
    }

    @Override
    public List<AmOperateLog> list(AmOperateLogQueryReqDto dto) {
        return operateLogDao.list(dto);
    }

    @Override
    public Page<AmOperateLog> findPage(AmOperateLogQueryReqDto dto) {
        Page<AmOperateLog> amOperateLogPage = operateLogDao.find(dto);
        List<AmOperateLog> datas = amOperateLogPage.getDatas();
        if(datas != null && !datas.isEmpty()){
            List<AmOperateLog> operateLogResDtoList = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(AmOperateLog operateLog : datas){
                AmOperateLogResDto operateLogResDto = new AmOperateLogResDto();
                BeanUtils.copyProperties(operateLog, operateLogResDto);
                operateLogResDto.setOperateTimeFormat(sdf.format(operateLog.getOperateTime()));
                operateLogResDtoList.add(operateLogResDto);
            }
            amOperateLogPage.setDatas(operateLogResDtoList);
        }
        return amOperateLogPage;
    }

    @Override
    public AmOperateLog findById(Integer id){
        return operateLogDao.get(id);
    }
}