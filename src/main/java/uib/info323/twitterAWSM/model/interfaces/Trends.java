package uib.info323.twitterAWSM.model.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Trends {

	public String getInsertedDateAndTime();

	public void setInsertedDateAndTime(String dateAndTime);

	public Map<Date, List<Trend>> getTrends();

	public void setTrends(Map<Date, List<Trend>> trends);

}
