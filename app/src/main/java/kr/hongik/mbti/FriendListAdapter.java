package kr.hongik.mbti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FriendVO> chatData;

    public FriendListAdapter(Context context, int textViewResourceId, ArrayList<FriendVO> list) {
        this.context = context;
        this.chatData = list;
    }

    @Override
    public Object getItem(int position) { // position번째 아이템
        return chatData.get(position);
    }

    @Override
    public long getItemId(int position) { // position번째 항목의 id인데 보통 position
        return position;
    }

    @Override
    public int getCount() { // 전체 데이터 개수
        return chatData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.user, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_profileImage =  (ImageView)convertView.findViewById(R.id.profileImage);
            viewHolder.tv_nickname = (TextView)convertView.findViewById(R.id.tv_nickname);
            viewHolder.tv_state_message = (TextView) convertView.findViewById(R.id.tv_state_message);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //setData
        viewHolder.tv_nickname.setText(chatData.get(position).getName());
        viewHolder.tv_state_message.setText(chatData.get(position).getStateMessage());

        return convertView;
    }
    
    private class ViewHolder {
        ImageView iv_profileImage;
        TextView tv_nickname;
        TextView tv_state_message;
    }
}
