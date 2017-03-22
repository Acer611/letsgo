package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.dao.team.HotelDao;
import com.umessage.letsgo.domain.po.team.HotelEntity;
import com.umessage.letsgo.domain.vo.system.request.HotelRequest;
import com.umessage.letsgo.service.api.team.IHotelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HotelServiceImpl implements IHotelService {
	@Resource
	private HotelDao hotelDao;

	@Override
	public List<HotelEntity> getHotel(HotelRequest hotel) {
		return hotelDao.getHotel(hotel);
	}

	@Override
	public HotelEntity selectById(String hotelId) {
		return hotelDao.selectById(hotelId);
	}

	@Override
	public List<HotelEntity> getHotelByName(String hotelName) {
		HotelRequest hotelRequest = new HotelRequest();
		hotelRequest.setHotelChineseName(hotelName);
		return hotelDao.getHotelByName(hotelRequest);
	}

	@Override
	public List<HotelEntity> getHotelByNameAndCityName(String hotelName, String cities) {
		HotelRequest hotelRequest = new HotelRequest();
		hotelRequest.setHotelChineseName(hotelName);
		hotelRequest.setCities(ELUtil.strToList(cities));
		return hotelDao.getHotelByNameAndCityName(hotelRequest);
	}

	public static void  main (String [] args){
		HotelServiceImpl   h=new HotelServiceImpl();
		h.getHotel(null);
	}
}
