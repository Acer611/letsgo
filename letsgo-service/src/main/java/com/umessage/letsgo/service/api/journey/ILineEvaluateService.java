package com.umessage.letsgo.service.api.journey;

import com.umessage.letsgo.domain.po.journey.LineEvaluateEntity;
import com.umessage.letsgo.domain.vo.journey.request.LineEvaluateRequest;
import com.umessage.letsgo.domain.vo.journey.response.LineEvaluateResponse;

/**
 * 线路评价Service接口
 * @author pw
 *
 */
public interface ILineEvaluateService {
	// 新增行程
	public int add(LineEvaluateEntity lineEvaluateEntity);
	// 删除行程
	public int delete(long id);

	//根据条件查询线路评价统计数据
	LineEvaluateResponse  getLineList(LineEvaluateRequest request);

}
