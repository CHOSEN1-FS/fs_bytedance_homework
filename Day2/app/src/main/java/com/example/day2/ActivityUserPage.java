package com.example.day2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.NonNull;

public class ActivityUserPage extends AppCompatActivity
{
    ImageView userprofile;
    TextView tv_username,tv_usersignature;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    private RecyclerView recyclerView;

    //需要适配的数据
    private int[] Icons = {R.drawable.profile,R.drawable.collection,R.drawable.history,
            R.drawable.settings,R.drawable.about_us,R.drawable.feedback2};

    private String[] Infos = {"个人信息","我的收藏","浏览历史","设置","关于我们","意见反馈"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        recyclerView = findViewById(R.id.rcv);
        recyclerView.setLayoutManager(new GridLayoutManager(ActivityUserPage.this,
                1, GridLayoutManager.VERTICAL, false));

        UserAdapter adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
        PresetUserInfoAndRead();
    }

    private void PresetUserInfoAndRead()
    {
        //头像设置
        userprofile = findViewById(R.id.id_iv_userprofile);
        userprofile.setImageResource(R.drawable.wechatprofile);


        //sp预设用户信息
        sp = getSharedPreferences("data",MODE_PRIVATE);
        edit = sp.edit();

        //先写入
        String username = "Aust1n";
        String signature = "挺好一男的";
        edit.putString("username",username);
        edit.putString("usersignature",signature);
        edit.commit();
        Toast.makeText(this,"用户信息预设成功！",Toast.LENGTH_SHORT).show();

        //在读取，并显示到对应控件位置
        username = sp.getString("username", "");
        signature = sp.getString("usersignature", "");

        tv_username= findViewById(R.id.id_tv_username);
        tv_usersignature = findViewById(R.id.id_tv_usersignature);
        tv_username.setText(username);
        tv_usersignature.setText(signature);
    }

    class UserAdapter extends RecyclerView.Adapter<MyViewHolder>
    {
        //创建一个ViewHolder
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            //inflate:视图
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(ActivityUserPage.this)
                    .inflate(R.layout.activity_recycleview_item,parent,false));
            return myViewHolder;
        }

        //把数据绑定到ViewHolder
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
        {
            holder.icon.setBackgroundResource(Icons[position]);
            holder.info.setText(Infos[position]);
            holder.itemView.setOnClickListener(v->
            {
                    Toast.makeText(v.getContext(),"按钮被点击: " + Infos[position],Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount()
        {
            return Infos.length;
        }
    }

    //匹配每个item数据
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        //声明
        ImageView icon;      //用户信息图标
        TextView info;     //用户信息菜单

        //构造器
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            icon = itemView.findViewById(R.id.id_iv_icon);
            info = itemView.findViewById(R.id.id_tv_info);
        }
    }
}
