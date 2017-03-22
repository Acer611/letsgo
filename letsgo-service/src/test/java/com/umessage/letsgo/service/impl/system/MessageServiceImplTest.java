package com.umessage.letsgo.service.impl.system;

/**
 * Created by ZhaoYidong on 2016/6/18.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
//        "classpath:spring/spring-mybatis.xml",
//        "classpath:spring/spring-beans.xml" })
//public class MessageServiceImplTest {
//
//    @Resource
//    private IMessageService messageService;
//
//    @Test
//    public void mailSendTest(){
//
//        TravelAgencyEntity travel = new TravelAgencyEntity();
//        travel.setName("天天旅行社");
//        travel.setContactPerson("天天");
//        travel.setContactPhone("18910582345");
//        travel.setEmail("18910582345@189.com");
//        travel.setAddress("天安门向东1000米");
//        travel.setDesc("天安门向东1000米天安门向东1000米天安门向东1000米天安门向东1000米天安门向东1000米天安门向东1000米天安门向东1000米天安门向东1000米");
//        travel.setLicenseUrl("https://rescdn.qqmail.com/zh_CN/htmledition/images/spacer1e9c5d.gif");
////        System.out.println(getMailContent(travel));
////        messageService.sendMailMessage(travel.getName()+"注册",getMailContent(travel));
//    }
//
////    private String getMailContent(TravelAgencyEntity travel){
////        StringBuffer sb = new StringBuffer();
////        sb.append("<html>");
////        sb.append("<body>");
////        sb.append("<table>");
////        sb.append("<tr><td>旅行社名称：</td>").append("<td>").append(travel.getName()).append("</td>").append("</tr>");
////        sb.append("<tr><td>联系人姓名：</td>").append("<td>").append(travel.getContactPerson()).append("</td>").append("</tr>");
////        sb.append("<tr><td>联系人电话：</td>").append("<td>").append(travel.getContactPhone()).append("</td>").append("</tr>");
////
////        sb.append("<tr><td>email：</td>").append("<td>").append(travel.getEmail()).append("</td>").append("</tr>");
////        sb.append("<tr><td>旅行社地址：</td>").append("<td>").append(travel.getAddress()).append("</td>").append("</tr>");
////        sb.append("<tr><td>简介：</td>").append("<td>").append(travel.getDesc()).append("</td>").append("</tr>");
////        sb.append("<tr><td>营业执照图片地址：</td>").append("<td>").append(travel.getLicenseUrl()).append("</td>").append("</tr>");
////
////        sb.append("</table>");
////        sb.append("</body>");
////        sb.append("</html>");
////        return sb.toString();
////    }
//}
