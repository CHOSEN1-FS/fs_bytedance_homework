package com.example.day2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day2.sqlite.MyHelper;

import org.jspecify.annotations.NonNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText et_email,et_pwd;
    Button btn_login,btn_wechat,btn_apple;
    ContentValues values;
    private MyHelper myHelper;
    SQLiteDatabase db;
    private static final String TABLE_NAME = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initView();

    }

    public void initView()
    {
        //控件初始化
        et_email = findViewById(R.id.id_et_email);
        et_pwd = findViewById(R.id.id_et_password);
        btn_login = findViewById(R.id.id_btn_login);
        btn_wechat = findViewById(R.id.id_btn_wechat_login);
        btn_apple = findViewById(R.id.id_btn_apple_login);

        //数据库初始化
        myHelper = new MyHelper(MainActivity.this);

        //sp初始化


        //设置按钮点击事件
        btn_login.setOnClickListener(this);
        btn_wechat.setOnClickListener(this);
        btn_apple.setOnClickListener(this);

        //登录界面预埋用户数据
        PreEmbed_UserInfo();
    }

    private void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


    private void PreEmbed_UserInfo()
    {
        //预埋用户信息（测试）--增
        db = myHelper.getWritableDatabase();
        values = new ContentValues();
        values.put("email","123");
        values.put("password","123");
        db.insert(TABLE_NAME,null,values);
        showToast("已成功预埋用户信息");
        db.close();
    }

    @Override
    public void onClick(View v)
    {
        //初始化
        String email = et_email.getText().toString();
        String pwd = et_pwd.getText().toString();

        //事件判断
        if(v.getId() == R.id.id_btn_login)
        {
            //普通登录toast，需要数据库验证
            //账号判断
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd))
            {
                showToast("请输入用户名和密码");
            }
            else
            {
                // 数据库验证--查

                Cursor cursor = null;

                try
                {
                    db = myHelper.getReadableDatabase();

                    // 正确的查询: 只查询匹配的用户
                    // 使用参数化查询防止SQL注入
                    String selection = "email = ? AND password = ?";
                    String[] selectionArgs = {email, pwd};

                    cursor = db.query(
                            TABLE_NAME,     // 表名
                            null,          // 要查询的列 (null表示所有列)
                            selection,     // WHERE条件
                            selectionArgs, // WHERE条件的参数
                            null,         // GROUP BY
                            null,         // HAVING
                            null          // ORDER BY
                    );
                    // 检查查询结果
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        showToast("登录成功!");
                        Intent intent = new Intent(MainActivity.this, ActivityUserPage.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        showToast("账号或密码错误!");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    showToast("数据库查询失败: " + e.getMessage());
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                    if (db != null && db.isOpen())
                    {
                        db.close();
                    }
                }
            }
        }
        else if(v.getId() == R.id.id_btn_wechat_login)
        {
            //微信登录toast提示
            showToast("微信toast");
        }
        else if(v.getId() == R.id.id_btn_apple_login)
        {
            //苹果登录toast提示
            showToast("苹果toast");
        }
        else
        {
            //错误提示
//            showToast("请输入用户名和密码");
        }
    }
}