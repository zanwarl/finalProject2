
package korea.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import korea.tour.model.tourCmtDTO;
import korea.tour.model.tourDAO;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;

@Controller
public class TourController {

	@Autowired
	private tourDAO tDAO;
	
	// 최상위 api 주소 세팅
	public final static String service_key = "76zE48jtXxj3nqHQhQfsoUjigjZE3n0lRkbHkszP0BJMJNqWzR3p3J2qJKCs7E70RYO9qSOmfM36DkozbFL6Dw%3D%3D";
	public static String tour_code = "areaCode";
	public static String param_1 = "&numOfRows=40&pageNo=1&MobileOS=ETC&MobileApp=TestApp";
	public static String data_type = "&_type=json"; // 사용하지 않을 시 XML 타입

	public static String tour_api_url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + tour_code
			+ "?ServiceKey=" + service_key + param_1 + data_type;

	JSONParser jsonParser = new JSONParser();

	// rest api 주소 실행하는 method
	private static String readUrl(String tour_api_url) throws Exception {
		//System.out.println(tour_api_url);
		BufferedInputStream reader = null;
		try {
			URL url = new URL(tour_api_url);
			reader = new BufferedInputStream(url.openStream());
			StringBuffer buffer = new StringBuffer();
			int i;
			byte[] b = new byte[4096];
			while ((i = reader.read(b)) != -1) {
				buffer.append(new String(b, 0, i));
			}
			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}
		
	//JSON
	@RequestMapping("/areaCode.do")
	public ModelAndView areaCode() throws Exception {

		tour_code = "areaCode";
		param_1 = "&contentTypeId=&" + tour_code + "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=TestApp";

		tour_api_url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + tour_code + "?ServiceKey="
				+ service_key + param_1 + data_type;

		JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(tour_api_url));
		JSONObject json = (JSONObject) jsonObject.get("response");
		json = (JSONObject) json.get("body");
		String totalCount = JSONValue.toJSONString(json.get("totalCount"));

		 //System.out.println("총 지역 수 : " +totalCount);
		//System.out.println(jsonObject);
		json = (JSONObject) json.get("items");
		JSONArray list = (JSONArray) json.get("item");
		JSONArray arrayName = new JSONArray();

		for (int i = 0; i < list.size(); i++) {

			JSONObject entity = (JSONObject) list.get(i);
			String name = (String) entity.get("name");
			// System.out.println(name);
			arrayName.add(name);

		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tour/areaCode");
		mav.addObject("jsonObject", jsonObject);
		mav.addObject("list", list);
		mav.addObject("arrayName", arrayName);
		return mav;
	}
	
	// 실제 페이지 이동
	@RequestMapping("/area.do")
	public ModelAndView areaList() {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("tour/cities");
		return mav;
	}

	// JSON
	@RequestMapping("/areaBasedList.do")
	public ModelAndView areaBasedList(@RequestParam(value = "areaCode") String areaCode) throws Exception {
		System.out.println("areaCode : " + areaCode);
		tour_code = "areaBasedList";
		param_1 = "&contentTypeId=&" + "areaCode=" + areaCode
				+ "&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp="
				+ "TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1";

		tour_api_url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + tour_code + "?ServiceKey="
				+ service_key + param_1 + data_type;

		JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(tour_api_url));
		
		JSONObject json = (JSONObject) jsonObject.get("response");
		json = (JSONObject) json.get("body");
		String totalCount = JSONValue.toJSONString(json.get("totalCount"));

		 System.out.println("총 지역 수 : " +totalCount);

		tour_api_url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/"
				+ tour_code
				+ "?ServiceKey="
				+ service_key
				+ param_1
				+ data_type;
		
		ModelAndView mav = new ModelAndView();

		mav.setViewName("tour/areaBasedList");
		mav.addObject("jsonObject", jsonObject);
		return mav;

	}

	// 실제 페이지 이동
	@RequestMapping("/city.do")
	public ModelAndView cityList(@RequestParam(value = "areaCode") String areaCode) throws Exception {

			ModelAndView mav = new ModelAndView();
			
			mav.setViewName("tour/cityList");
			mav.addObject("areaCode",areaCode);
			return mav;
		}
	
	@RequestMapping("/tourDetailJSON.do")
	public ModelAndView tourDetailJSON(@RequestParam(value = "contentId") String contentId, 
			@RequestParam(value = "contentTypeId") String contentTypeId) throws Exception{
		
		System.out.println("contentid : " + contentId);
		tour_code = "detailCommon";
		param_1 = "&contentTypeId=" + contentTypeId + "&contentId=" + contentId
				+ "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y"
				+ "&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";

		tour_api_url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + tour_code + "?ServiceKey="
				+ service_key + param_1 + data_type;

		JSONObject jsonObject = (JSONObject) jsonParser.parse(readUrl(tour_api_url));

		ModelAndView mav = new ModelAndView();

		mav.setViewName("tour/contentid");
		mav.addObject("jsonObject", jsonObject);
		return mav;

	}

	//여행지 페이지 상세 정보 + 댓글 리스트
	@RequestMapping("/tourDetail.do")
	public ModelAndView tourDetail(@RequestParam(value = "contentId",required=false,defaultValue="") String contentId, 
			@RequestParam(value = "contentTypeId",required=false,defaultValue="") String contentTypeId, @RequestParam(value="cp",required=false,defaultValue="1")int cp,
			@RequestParam(value="queryStr",required=false,defaultValue="")String queryStr) {
		
		int row = tDAO.totalCnt();
		
		int pageRow = 5; //한 페이지에서 보여질 게시물 수
		int pageBlock = 5; //한 페이지에서 보여질 페이지 수
		
		String url = "tourDetail.do?contentTypeId=" + contentTypeId + "&contentId=" + contentId;
		//System.out.println("url : " + url);
		
		String page = korea.page.PageModule.makePage(url,row,pageRow,pageBlock,cp,url);
		
		
		List<tourCmtDTO> list = tDAO.tourcmtList(contentId,cp,pageRow);
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("tour/tourDetail");
		mav.addObject("contentId", contentId);
		mav.addObject("list", list);
		mav.addObject("page", page);
		return mav;
	}
	
	// 실제 페이지 이동
	@RequestMapping("/tour.do")
	public ModelAndView tourList() throws Exception {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("tour/tourList");

		return mav;
	}
	
	@RequestMapping("/tourMap.do")
	public ModelAndView tourMap(@RequestParam(value="mapx") String mapx, @RequestParam(value="mapy") String mapy) {
		//System.out.println(mapx + "/" + mapy);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("mapx", mapx);
		mav.addObject("mapy", mapy);
		mav.setViewName("tour/tourMap");
		return mav;
	}
	
	@RequestMapping("/tourCmtWrite.do")
	public ModelAndView replyWrite(HttpServletRequest hsr, tourCmtDTO cmtdto) {
		//System.out.println("내용 : " + cmtdto.getTour_cmt_content());
		//System.out.println(hsr.getRequestURI());

		
		ModelAndView mav = new ModelAndView();
		int result = tDAO.tourcmtWrite(cmtdto);
		String msg = result>0?"댓글 작성 완료":"댓글 작성 실패";
		mav.addObject("msg", msg);
		mav.setViewName("tour/tourMsgOk");
		return mav;
		
	}

}
