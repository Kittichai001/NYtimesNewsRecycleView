package chai.nytimesnewsrecycleview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chai on 8/6/18.
 */

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.MyViewHolder> implements Filterable {
    List<String> mData;
    List<String> mStringFilterList;
    List<String> icon;
    static List<String> urlList;
    ValueFilter valueFilter;
    static Context context;

    public AdapterRecycle(List<String> mData, List<String> icon, List<String> urlList,Context context) {
        this.mData=mData;
        mStringFilterList = mData;
        this.icon = icon;
        this.urlList = urlList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewMy = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(viewMy);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position));
        if(icon.get(position) != null)
            Picasso.with(context).load(icon.get(position)).fit().into(holder.im_droid);
        else
            holder.im_droid.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
        TextView tv_name;
        ImageView im_droid;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_info);
            im_droid = (ImageView) itemView.findViewById(R.id.im_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            getDetailPage();
        }

        @Override
        public boolean onLongClick(View v) {
            getDetailPage();
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            getDetailPage();
            return false;
        }

        private void getDetailPage(){
            Bundle bundle = new Bundle();
            bundle.putString("web_url",urlList.get(getAdapterPosition()));

            Intent showItemIntent = new Intent(context,
                    NewsDescActivity.class);
            showItemIntent.putExtras(bundle);
            context.startActivity(showItemIntent);
        }
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mData = (List<String>) results.values;
            notifyDataSetChanged();
        }

    }
}
