package chai.nytimesnewsrecycleview.Service;

/**
 * Created by chai on 8/6/18.
 */

import java.util.List;

public class Doc {

    public String web_url;
    public String snippet;
    public Blog blog;
    public String source;
    public List<Multimedium> multimedia = null;
    public Headline headline;
    public List<Keyword> keywords = null;
    public String pub_date;
    public String document_type;
    public String news_desk;
    public String section_name;
    public Byline byline;
    public String type_of_material;
    public String _id;
    public int word_count;
    public int score;
    public String uri;
}
