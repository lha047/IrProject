package uib.info323.twitterAWSM.model.interfaces;

import java.util.Date;
import java.util.List;

public interface IReply {

	public Date getDate();

	public String getText();

	public String getFromUserId();

	public List<String> getTags();

	public List<String> getMentions();
}
