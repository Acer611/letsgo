package com.umessage.letsgo.service.impl.journey;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.umessage.letsgo.dao.journey.LineEvaluateDao;
import com.umessage.letsgo.domain.po.journey.LineEvaluateEntity;
import com.umessage.letsgo.domain.vo.journey.request.LineEvaluateRequest;
import com.umessage.letsgo.domain.vo.journey.response.LineEvaluateResponse;
import com.umessage.letsgo.domain.vo.journey.response.LineListVo;
import com.umessage.letsgo.service.api.journey.ILineEvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;

@Service
public class LineEvaluateServiceImpl implements ILineEvaluateService {

	@Resource
	private LineEvaluateDao lineEvaluateDao;

	@Override
	public int add(LineEvaluateEntity lineEvaluateEntity) {
		Date date = new Date();
		lineEvaluateEntity.setCreateTime(date);
		return lineEvaluateDao.insert(lineEvaluateEntity);
	}

	@Override
	public int delete(long id) {
		return lineEvaluateDao.delete(id);
	}

	//根据条件查询线路评价统计数据
	@Override
	public LineEvaluateResponse  getLineList(LineEvaluateRequest request){
		//关联查分页
		PageHelper.startPage(request.getPageNum(),request.getPageSize());
		Page<LineEvaluateEntity> lineEvaluateEntity = lineEvaluateDao.getLineList(request);
		Page<LineListVo> lineList =new Page<LineListVo>();
		if(lineEvaluateEntity!=null && lineEvaluateEntity.size() >0){
			LineListVo vo =new LineListVo();
			vo.setId(0L);
			//线路
			vo.setLineName(request.getCountry());
			//时间
			vo.setDate(request.getStartDate().toString()+"-"+request.getEndDate().toString());
			//出团次数
			vo.setTravelCount(lineEvaluateEntity.size());
			int allCount=0;
			double allScore=0;
			for (int i = 0; i < lineEvaluateEntity.size(); i++) {
				LineEvaluateEntity er =lineEvaluateEntity.get(i);
				if(er!=null){
					//评价总人数
					allCount+=er.getEvaluateNum();
					//总评分
					allScore+=er.getGrade();
				}
			}
			//评价总人数
			vo.setEvaluateCount(allCount);
			//综合评分
			DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
			String grades="0.0";
			if(allCount>0 && allScore>0) {
				double grade = allScore/ allCount;
				grades=df.format(grade);
			}
			vo.setTotalScore(Double.parseDouble(grades));
			lineList.add(vo);
		}

		LineEvaluateResponse response = new  LineEvaluateResponse();
		response.setPages(1);
		response.setTotals(1);
		response.setLineList(lineList);
		return response;
	}
}
