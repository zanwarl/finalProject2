package korea.tour.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public class tourDAOImple implements tourDAO {

	private SqlSessionTemplate sqlMap;

	public tourDAOImple(SqlSessionTemplate sqlMap) {
		super();
		this.sqlMap = sqlMap;
	}
	
	public int tourcmtWrite(tourCmtDTO cmtdto) {
		System.out.println("코드번호 " + cmtdto.getTour_cmt_pidx());
		int result = sqlMap.insert("tourCmtWrite", cmtdto);
		return result;
	}
	
	public int totalCnt() {
		int result = sqlMap.selectOne("tourTotalCnt");
		System.out.println("otalcnt" + result);
		return result;
	}
	public List<tourCmtDTO> tourcmtList(String tour_cmt_pidx, int cp, int pageRow) {
		//System.out.println("sql : " + tour_cmt_pidx);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tour_cmt_pidx", tour_cmt_pidx);
		map.put("cp", cp);
		map.put("pageRow", pageRow);
		
		List<tourCmtDTO> list = sqlMap.selectList("tourCmtList", map);
		return list;
	}
}
