package chai.nytimesnewsrecycleview.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by chai on 8/6/18.
 */

public interface NewsService {
    @GET("articlesearch.json")
    public Call<Example> search  (@Query("api-key") String apikey,
                               @Query("sort") String sort);
}
