package rai.arpit.wikipic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rai_Sahab on 6/4/2016.
 */
public class CustomImageViewAdapter extends BaseAdapter {
    private List<Image_POJO> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private final String TAG="CustomAdapter";
    public CustomImageViewAdapter(Context context, ArrayList<Image_POJO> list) {
        this.context=context;
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        ImageView picture;
        TextView name;
        if(v==null) {
            v = layoutInflater.inflate(R.layout.grid_item_view, parent, false);
            v.setTag(R.id.image_view, v.findViewById(R.id.image_view));
            v.setTag(R.id.imageCaption, v.findViewById(R.id.imageCaption));
        }
        picture=(ImageView)v.getTag(R.id.image_view);
        name=(TextView)v.getTag(R.id.imageCaption);

        Log.e(TAG,(list.get(position)).getSrc());

        if((list.get(position)).getSrc().equals("NO_IMAGE")){
            (list.get(position)).setSrc("https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/ArafatEconomicForum.jpg/277px-ArafatEconomicForum.jpgwid");
        }

        //Picasso is better framework to upload image from a uri
        Picasso.with(context).load(
                (list.get(position)).getSrc()
                )
                .placeholder(R.drawable.no_image_thumb)
                .error(R.drawable.no_image_thumb)
                .into(picture);

     //   picture.setImageResource((list.get(position)).getSrc());

        name.setText((list.get(position)).getTitle());
        return v;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getItemTitle(int position) {
        return list.get(position).getTitle();
    }

}
