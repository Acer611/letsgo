package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.UserTagDao;
import com.umessage.letsgo.domain.po.security.TagsEntity;
import com.umessage.letsgo.domain.po.security.UserTagEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.TagsRequest;
import com.umessage.letsgo.service.api.security.ITagsService;
import com.umessage.letsgo.service.api.security.IUserTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wendy on 2016/9/8.
 */
@Service
public class UserTagServiceImpl implements IUserTagService {

    @Resource
    private UserTagDao userTagDao;
    @Resource
    private ITagsService tagsService;

    @Override
    public int add(UserTagEntity userTagEntity) {
        userTagEntity.setCreateTime(new Date());
        userTagEntity.setVersion(0l);
        return userTagDao.insert(userTagEntity);
    }

    @Override
    @Transactional
    public int update(UserTagEntity userTagEntity) {
        userTagEntity.setCreateTime(new Date());
        return userTagDao.update(userTagEntity);
    }

    //给好友添加标签
    @Override
    public CommonResponse saveTags(TagsRequest request){
        if(request==null){
            return CommonResponse.createNotFoundResponse("请传所需参数");
        }
        if(StringUtils.isEmpty(request.getName())){
            deleteUserTagByLabeledUserIdAndUserId(request);
            return new CommonResponse();
        }
        String [] names = request.getName().split(";");
        if(names!=null && names.length>0){
             deleteUserTagByLabeledUserIdAndUserId(request);
            for (String str:names) {
                if(!StringUtils.isEmpty(str)){
                    TagsRequest req=new TagsRequest();
                    req.setUserId(request.getUserId());
                    req.setName(str);
                    TagsEntity  tags = tagsService.getTagsByNameAndUserId(req);
                    if(tags!=null){
                            UserTagEntity userTags = new UserTagEntity();
                            //好友ID
                            userTags.setLabeledUserId(request.getLabeledUserId());
                            //登陆用户ID
                            userTags.setLabellingUserId(request.getUserId());
                            userTags.setTagId(tags.getId());
                            add(userTags);

                    }else{
                        TagsEntity tag=new TagsEntity();
                        tag.setName(str);
                        tag.setUserId(request.getUserId());
                        tagsService.add(tag);
                            UserTagEntity userTags = new UserTagEntity();
                            //好友ID
                            userTags.setLabeledUserId(request.getLabeledUserId());
                            //登陆用户ID
                            userTags.setLabellingUserId(request.getUserId());
                            userTags.setTagId(tag.getId());
                            add(userTags);
                    }
                }
            }
        }else{
            return CommonResponse.createNotFoundResponse("请传所需参数");
        }
        return new CommonResponse();
    }

    //通过用户ID 和好友ID 删除
    public  int deleteUserTagByLabeledUserIdAndUserId(TagsRequest request){
        return userTagDao.deleteUserTagByLabeledUserIdAndUserId(request);
    }
    //通过标签ID  删除
    public int deleteUserTagByTagId(Long tagId){
        TagsRequest request = new TagsRequest();
        request.setTagId(tagId);
        return userTagDao.deleteUserTagByTagId(request);
    }
}
