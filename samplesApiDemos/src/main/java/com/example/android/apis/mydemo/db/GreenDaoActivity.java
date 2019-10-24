package com.example.android.apis.mydemo.db;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.apis.R;
import com.example.android.apis.mydemo.db.model.DaoMaster;
import com.example.android.apis.mydemo.db.model.DaoSession;
import com.example.android.apis.mydemo.db.model.Student;

import java.util.List;

public class GreenDaoActivity extends Activity implements View.OnClickListener {
    private static final String TAG = GreenDaoActivity.class.getSimpleName();

    private EditText mEtName;
    private EditText mEtGender;
    private Button mBtnInsert;
    private Button mBtnSearch;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private DaoSession mDaoSession;
    private DaoMaster mDaoDb;
    private List<Student> students;
    private ArrayAdapter<Student> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        initView();

        // 初始化greendao
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "student.db");
        mDaoDb = new DaoMaster(openHelper.getWritableDb());
        mDaoSession = mDaoDb.newSession();

        search();
    }

    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtGender = (EditText) findViewById(R.id.et_gender);
        mBtnInsert = (Button) findViewById(R.id.btn_insert);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mListView = (ListView) findViewById(R.id.listView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mBtnInsert.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_search:
                search();
                break;
        }
    }

    /**
     * 插入数据
     */
    private void insert() {
        String name = mEtName.getText().toString();
        String gender = mEtGender.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        int genderInt = Integer.parseInt(gender);
        showProgressBar();
        Student student = new Student();
        student.setGender(genderInt);
        student.setName(name);
        long id = mDaoSession.insert(student);
        Toast.makeText(this, "添加成功,id="+id, Toast.LENGTH_SHORT).show();
        hideProgressBar();

        mEtName.setText("");
        mEtGender.setText("");
        //查询显示ListView
        // TODO 正式开发不建议查询所有，而是添加成功直接想List中插入一条数据
        // search();

        if(students != null){
            mAdapter.insert(student, students.size());
//            students.add(student);
        }
    }

    /**
     * 查询数据
     */
    private void search() {
        showProgressBar();
        students = mDaoSession.queryBuilder(Student.class).list();
        Log.d(TAG, students.toString());
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students);
        mListView.setAdapter(mAdapter);
        hideProgressBar();

    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}
