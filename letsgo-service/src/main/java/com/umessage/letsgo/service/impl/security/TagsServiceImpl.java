package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.TagsDao;
import com.umessage.letsgo.domain.po.security.TagsEntity;
import com.umessage.letsgo.domain.vo.security.request.TagRequest;
import com.umessage.letsgo.domain.vo.security.request.TagsRequest;
import com.umessage.letsgo.domain.vo.security.respone.TagsResponse;
import com.umessage.letsgo.service.api.security.ITagsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wendy on 2016/9/8.
 */
@Service
public class TagsServiceImpl implements ITagsService {

    @Resource
    private TagsDao tagsDao;

    @Transactional
    @Override
    public int add(TagsEntity tagsEntity) {
        tagsEntity.setCreateTime(new Date());
        tagsEntity.setVersion(0l);
        return tagsDao.insert(tagsEntity);
    }
    public int delete(Long id) {
        return tagsDao.delete(id);
    }
    /**
     * 获取用户标签列表
     * @param viewUserId
     * @param currentLoginUserId
     * @return
     */
    @Override
    public List<String> getTagNameByUserId(Long viewUserId, Long currentLoginUserId) {
        TagRequest request = new TagRequest();
        request.setLabeledUserId(viewUserId);
        request.setLabellingUserId(currentLoginUserId);

        List<String> tagNames = tagsDao.getTagNameByUserId(request);
        if (CollectionUtils.isEmpty(tagNames)) {
            return new ArrayList<>();
        }
        return tagNames;
    }

    //根据用户ID获取 标签
    @Override
    public TagsResponse getTags(Long userId){
        List<TagsEntity> tags=tagsDao.getTags(userId);
        TagsResponse response =new TagsResponse();
         response.setTags(tags);
        return response;
    }

    //通过标签名称 和用户ID  获取标签对象
    @Override
    public TagsEntity  getTagsByNameAndUserId(TagsRequest request){
        return tagsDao.getTagsByNameAndUserId(request);
    }

}
