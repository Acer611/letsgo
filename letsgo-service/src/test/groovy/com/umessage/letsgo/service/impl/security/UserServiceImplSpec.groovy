/**
 * 
 */
package com.umessage.letsgo.service.impl.security

import com.umessage.letsgo.dao.security.UserDao
import spock.lang.Specification

/**
 * @author zhajl
 *
 */
public class UserServiceImplSpec extends Specification{

    UserDao userDao;

    UserServiceImpl userServiceImpl;
    UserRoleServiceImpl userRoleService;
    /*
    @Unroll("getUserByLoginAccount #loginAccount")
    def "getUserByLoginAccount should return user"() {
        given:
        UserEntity userEntity = new UserEntity(id:1, userName: "zhajl", phone: "13488786266", mail: "zhajl@umessage.com.cn");

        userDao = Mock();
        userDao.selectWithConditions("zhajl") >> userEntity;
        userDao.selectWithConditions("13488786266") >> userEntity;
        userDao.selectWithConditions("zhajl@umessage.com.cn") >> userEntity;
        //userDao.selectWithConditions("13712345678") >> null;
        //userDao.selectWithConditions("bobo") >> null;
        //userDao.selectWithConditions("bobo@umessage.com.cn") >> null;
        userServiceImpl = new UserServiceImpl(userDao : userDao);

        when:
        UserResponse userResponse = userServiceImpl.getUserByLoginAccount(loginAccount);

        then:
        userResponse.retCode == retCode;
        (userResponse.userEntity != null) == userExists;

        where:
        loginAccount    || retCode | userExists
        "zhajl"         || 0       | true
        "13488786266"   || 0       | true
        "zhajl@umessage.com.cn"   || 0       | true
        "bobo"          || 7       | false
        "13712345678"   || 7       | false
        "bobo@umessage.com.cn"   || 7       | false
    }

    @Unroll("getUserByLoginAccount2 #loginAccount")
    def "getUserByLoginAccount2 should return user"() {
        given:
        UserEntity userEntity = new UserEntity(id:1, userName: "zhajl", phone: "13488786266", mail: "zhajl@umessage.com.cn");
        userDao = Mock();
        userServiceImpl = new UserServiceImpl(userDao : userDao);

        when:
        UserResponse userResponse = userServiceImpl.getUserByLoginAccount(loginAccount);

        then:
        userResponse.retCode == retCode;
        (userResponse.userEntity != null) == userExists;
        1 * userDao.selectWithConditions(_) >> userEntity;

        where:
        loginAccount    || retCode | userExists
        "zhajl"         || 0       | true
        "13488786266"   || 0       | true
        "zhajl@umessage.com.cn"   || 0       | true
    }

    @Unroll("userLogin #userLogin")
    def "user login by username and password test"() {
        given:
        UserEntity userEntity = new UserEntity(id:1, userName: "zhajl",password: "123456", phone: "13488786266", mail: "zhajl@umessage.com.cn");

        UserLoginRequest  correctUserAndCorrectPwd = new UserLoginRequest(loginAccount:"zhajl",password: "123456",loginType: "1");
        UserLoginRequest  errorUser= new UserLoginRequest(loginAccount:"wahaha",password: "123456",loginType: "1");
        UserLoginRequest  errorPwd= new UserLoginRequest(loginAccount:"zhajl",password: "123457",loginType: "1");

        RoleEntity role = new RoleEntity(id:1,rolename:"领队");
        UserRoleEntity userRole = new UserRoleEntity(role: role,userId: 1);
        List<UserRoleEntity> userRoleList = new ArrayList<UserRoleEntity>();
        userRoleList.add(userRole);

        userDao = Mock(UserDao.class);
        userDao.selectWithConditions("zhajl") >> userEntity;
        userDao.selectWithConditions("wahaha") >> null;

        userRoleService = Mock(UserRoleServiceImpl.class);
        userRoleService.getUserRoleByUserId(userEntity.getId()) >> userRoleList;

        userServiceImpl = new UserServiceImpl(userDao : userDao,userRoleService: userRoleService);
        //正常登录
        when:
        UserLoginResponse userResponse = userServiceImpl.userLogin(correctUserAndCorrectPwd);

        then:
        userResponse.getRetCode() == 0;
        userResponse.getRetMsg() == "success";
        userResponse.getUserName() == "zhajl";
        if(userResponse.getUserRole() != null) userResponse.getUserRole().get(0) == "领队" ;
        //用户名错误
        when:
        UserLoginResponse userResponse2 = userServiceImpl.userLogin(errorUser);

        then:
        userResponse2.getRetCode() == 7;
        userResponse2.getRetMsg() == "找不到对应的用户";
        userResponse2.getUserName() == null;
        userResponse2.getUserRole() == null
        //密码错误
        when:
        UserLoginResponse userResponse3 = userServiceImpl.userLogin(errorPwd);

        then:
        userResponse3.getRetCode() == 3;
        userResponse3.getRetMsg() == "用户名或密码不正确";
        userResponse3.getUserName() == null;
        userResponse3.getUserRole() == null;
    }

    @Unroll("userLogin #userLogin")
    def "user login by code test"() {
        given:
        UserLoginRequest  errorCode = new UserLoginRequest(loginAccount:"15135124578",code: "123456",loginType: "2");
        UserLoginRequest  nonmember = new UserLoginRequest(loginAccount:"15135124578",code: "123457",loginType: "2");

        ValidationCodeEntity codeEntity = new ValidationCodeEntity(code:"123457",phone: "15135124578");

        IValidationCodeService validationCodeService = Mock();
        validationCodeService.getValidCodeByPhone("15135124578") >> codeEntity;

        IMemberService memberService = Mock();
        memberService.hasMemberInTeam("15135124578") >> Collections.emptyList();

        userServiceImpl = new UserServiceImpl(validationCodeService:validationCodeService,memberService:memberService);

        //验证码不正确
        when:
        UserLoginResponse userResponse = userServiceImpl.userLogin(errorCode);

        then:
        userResponse.getRetCode() == 1001;
        userResponse.getRetMsg() == "手机验证码不正确或已过期";

        //验证码正确，但不是会员。
        when:
        UserLoginResponse userResponse2 = userServiceImpl.userLogin(nonmember);

        then:
        userResponse2.getRetCode() == 8;
        userResponse2.getRetMsg() == "由于未受邀请暂时无法登录，请期待后续功能的陆续开放";
    }
    */

	
}
