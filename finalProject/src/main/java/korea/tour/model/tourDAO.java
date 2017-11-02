package korea.tour.model;

import java.util.List;

public interface tourDAO {
	
	public int tourcmtWrite(tourCmtDTO cmtdto);
	public int totalCnt();
	public List<tourCmtDTO> tourcmtList(String tour_cmt_pidx, int cp, int pageRow);
	
}
