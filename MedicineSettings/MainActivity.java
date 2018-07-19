package com.example.caucse.wpcnf;

import java.io.File;

        import java.util.ArrayList;



        import android.app.Activity;

        import android.content.ContentValues;

        import android.database.Cursor;

        import android.database.sqlite.SQLiteDatabase;

        import android.os.Bundle;

        import android.util.Log;

        import android.view.View;

        import android.view.View.OnClickListener;

        import android.view.Window;

        import android.widget.Button;

        import android.widget.EditText;

        import android.widget.ListView;

        import android.widget.TextView;

        import android.widget.Toast;



public class MainActivity extends Activity {



    MySQLiteOpenHelper helper;

    SQLiteDatabase db;

    ArrayList<MyMedicine> al = new ArrayList<MyMedicine>();

    Button btnInsert, btnDelete, btnUpdate, btnSelectAll, btnRun;

    EditText et1, et2, et3;

    TextView tv;

    int state = 0;

    MyAdapter adapter;

    ListView lv;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);





        btnInsert = (Button) findViewById(R.id.btn_insert);

        btnDelete = (Button) findViewById(R.id.btn_delete);

        btnUpdate = (Button) findViewById(R.id.btn_update);

        btnSelectAll = (Button) findViewById(R.id.btn_selectAll);

        btnRun = (Button) findViewById(R.id.btn_run);

        et1 = (EditText) findViewById(R.id.editText1);

        et2 = (EditText) findViewById(R.id.editText2);

        et3 = (EditText) findViewById(R.id.editText3);

        tv = (TextView) findViewById(R.id.textView_title);

        lv = (ListView) findViewById(R.id.listView1);



        // db 연결

        helper = new MySQLiteOpenHelper(MainActivity.this, "park.db", null, 1);



        // file이 생성 되어있지 않을 경우 파일 생성을 위해 더미 데이터 생성

        String fileChk = "/data/data/com.example.test054sqlite/databases/park.db";

        File file = new File(fileChk);

        if (!file.exists()) {

            insert("XJR", "Jang", 7777);

            insert("K5", "Seo", 3333);

            insert("YFSonata", "Ryu", 2222);

            insert("Avante", "Park", 8888);

            insert("Tico", "Lim", 0000);

        }



        // insert

        btnInsert.setOnClickListener(new OnClickListener() {



            @Override

            public void onClick(View v) {

                clear();

                et1.setEnabled(true);

                et2.setEnabled(true);

                et3.setEnabled(true);

                tv.setText("차량 추가");

                et1.setHint("차량 이름 입력");

                et2.setHint("차주 입력");

                et3.setHint("차량번호 입력");

                Toast.makeText(MainActivity.this, "정보입력 후 실행 입력",

                        Toast.LENGTH_SHORT).show();

                state = 1;

            }

        });



        // delete

        btnDelete.setOnClickListener(new OnClickListener() {



            @Override

            public void onClick(View v) {

                clear();

                et1.setEnabled(false);

                et2.setEnabled(false);

                et3.setEnabled(true);

                tv.setText("차량 삭제");

                et1.setHint("-");

                et2.setHint("-");

                et3.setHint("삭제할 차량번호 입력");

                Toast.makeText(MainActivity.this, "차량번호 입력 후 실행 입력",

                        Toast.LENGTH_SHORT).show();

                state = 2;

            }

        });



        // update

        btnUpdate.setOnClickListener(new OnClickListener() {



            @Override

            public void onClick(View v) {

                clear();

                et1.setEnabled(true);

                et2.setEnabled(true);

                et3.setEnabled(true);

                tv.setText("차량 수정");

                et1.setHint("수정할 차량명 입력");

                et2.setHint("수정할 차주명 입력");

                et3.setHint("수정할 차량번호 입력");

                Toast.makeText(MainActivity.this, "정보 입력 후 실행 입력",

                        Toast.LENGTH_SHORT).show();

                state = 3;

            }

        });



        // selectAll

        btnSelectAll.setOnClickListener(new OnClickListener() {



            @Override

            public void onClick(View v) {

                al.clear();

                select();

                adapter = new MyAdapter(MainActivity.this, al, R.layout.row);

                lv.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "DB 조회 완료", Toast.LENGTH_SHORT).show();

            }

        });



        // Run

        btnRun.setOnClickListener(new OnClickListener() {



            @Override

            public void onClick(View v) {

                switch (state) {



                    case 0: // 미사용

                        Toast.makeText(MainActivity.this, "상단 메뉴 선택 후 사용 하세요",

                                Toast.LENGTH_SHORT).show();

                        break;



                    case 1: // insert

                        if (et1.getText().toString().equals("")

                                || et2.getText().toString().equals("")

                                || et3.getText().toString().equals("")) {

                            Toast.makeText(MainActivity.this, "빈칸 두지 마세요",

                                    Toast.LENGTH_SHORT).show();

                            break;

                        }

                        long result1 = insert(et1.getText().toString(), et2

                                .getText().toString(), Integer.parseInt(et3

                                .getText().toString()));

                        if (result1 == -1) {

                            Toast.makeText(MainActivity.this, "잘못된 입력이 있습니다.",

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(MainActivity.this, "정상 처리되었습니다.",

                                    Toast.LENGTH_SHORT).show();

                        }

                        break;



                    case 2: // delete

                        if (et3.getText().toString().equals("")) {

                            Toast.makeText(MainActivity.this, "빈칸 두지 마세요",

                                    Toast.LENGTH_SHORT).show();

                            break;

                        }

                        int result2 = delete(Integer.parseInt(et3.getText()

                                .toString()));

                        if (result2 == 0) {

                            Toast.makeText(MainActivity.this, "잘못된 입력이 있습니다.",

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(MainActivity.this, "정상 처리되었습니다.",

                                    Toast.LENGTH_SHORT).show();

                        }

                        break;



                    case 3: // update

                        if (et1.getText().toString().equals("")

                                || et2.getText().toString().equals("")

                                || et3.getText().toString().equals("")) {

                            Toast.makeText(MainActivity.this, "빈칸 두지 마세요",

                                    Toast.LENGTH_SHORT).show();

                            break;

                        }

                        int result3 = update(et1.getText().toString(), et2

                                .getText().toString(), Integer.parseInt(et3

                                .getText().toString()));

                        if (result3 == 0) {

                            Toast.makeText(MainActivity.this, "잘못된 입력이 있습니다.",

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(MainActivity.this, "정상 처리되었습니다.",

                                    Toast.LENGTH_SHORT).show();

                        }

                        break;

                    default:

                }

            }

        });



    }



    // 그냥 중복되어 별도로 뺀 기능

    void clear() {

        et1.setText("");

        et2.setText("");

        et3.setText("");

    }



    // insert

    public long insert(String medicine_kind, String period, int num) {

        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("medicine_kind", medicine_kind);

        values.put("period", period);

        values.put("num", num);



        long result = db.insert("park", null, values);

        Log.i("SQL ", "insert : " + "(medicine_kind:" + medicine_kind + ""

                + ")(period:" + period + ")" + "(num:" + num + ")");

        return result;

    }



    // delete

    public int delete(int num) {

        db = helper.getWritableDatabase();

        int result = db.delete("park", "num=?", new String[] { num + "" });

        return result;

    }



    // update

    public int update(String medicine_kind, String period, int num) {

        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("medicine_kind", medicine_kind);

        values.put("period", period);

        int result = db.update("park", values, "num=?",

                new String[] { num + "" });

        return result;

    }



    // select

    public void select() {

        db = helper.getReadableDatabase();

        Cursor c = db.query("park", null, null, null, null, null, null);

        while (c.moveToNext()) {

            int id = c.getInt(c.getColumnIndex("id"));

            String medicine_kind = c.getString(c.getColumnIndex("medicine_kind"));

            String period = c.getString(c.getColumnIndex("period"));

            int num = c.getInt(c.getColumnIndex("num"));



            Log.i("SQL ", "select : " + "(id:" + id + ")(medicine_kind:" + medicine_kind + "), " +

                    "(period:" + period + ")" + "(num:" + num + ")");



            MyMedicine m = new MyMedicine();

            m.id = id;

            m.medicine_kind = medicine_kind;

            m.period = period;

            m.num = num;

            al.add(m);

        }

    }

}
