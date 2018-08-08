package chai.nytimesnewsrecycleview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import chai.nytimesnewsrecycleview.Service.Example;
import chai.nytimesnewsrecycleview.Service.NewsService;
import chai.nytimesnewsrecycleview.Service.NewsServiceFactory;
import chai.nytimesnewsrecycleview.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    RecyclerView mRecyclerView;
    List<String> arrayList= new ArrayList<>();
    List<String> urlList = new ArrayList<>();
    List<String> iconList = new ArrayList<>();
    AdapterRecycle adapterRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final NewsService newsService = NewsServiceFactory.getInstance();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Call<Example> call = newsService.search(NewsConstant.API_KEY, "newest");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    Example ob = response.body();
                    for(int i=0;i<ob.response.docs.size();i++) {
                        arrayList.add(ob.response.docs.get(i).headline.print_headline);
                        urlList.add(ob.response.docs.get(i).web_url);
                        String iconUrl=null;
                        if(ob.response.docs.get(i).multimedia.size() > 0 && ob.response.docs.get(i).multimedia.get(0).url != null)
                            iconUrl = "https://www.nytimes.com/"+ob.response.docs.get(i).multimedia.get(0).url;
                        iconList.add(iconUrl);
                    }
                    prepListView();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<String> icon = new ArrayList<>();
        for (int i = 0; i <= arrayList.size(); i++) {
            icon.add(i, "");
        }
        adapterRecycle = new AdapterRecycle( arrayList, icon,urlList, getApplicationContext());
        mRecyclerView.setAdapter(adapterRecycle);
//        activityMainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        activityMainBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapterRecycle = new AdapterRecycle( arrayList, icon);
//        activityMainBinding.recyclerView.setAdapter(adapterRecycle);
        activityMainBinding.search.setActivated(true);
        activityMainBinding.search.setQueryHint("Type your keyword here");
        activityMainBinding.search.onActionViewExpanded();
        activityMainBinding.search.setIconified(false);
        activityMainBinding.search.clearFocus();

        activityMainBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterRecycle.getFilter().filter(newText);

                return false;
            }
        });
    }

    private void prepListView(){
        adapterRecycle= new AdapterRecycle(arrayList, iconList,urlList, getApplicationContext());
        activityMainBinding.recyclerView.setAdapter(adapterRecycle);
        activityMainBinding.search.setActivated(true);
        activityMainBinding.search.setQueryHint("Type your keyword here");
        activityMainBinding.search.onActionViewExpanded();
        activityMainBinding.search.setIconified(false);
        activityMainBinding.search.clearFocus();

        activityMainBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterRecycle.getFilter().filter(newText);

                return false;
            }
        });


    }
}
