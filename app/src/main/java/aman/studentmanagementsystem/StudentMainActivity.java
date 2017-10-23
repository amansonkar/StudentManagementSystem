package aman.studentmanagementsystem;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StudentMainActivity extends Activity {
    EditText ename,eroll_no,esemester,emob_no,eothers;
    Button add,view,viewall,Show1,delete,modify;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ename=(EditText)findViewById(R.id.name);
        eroll_no=(EditText)findViewById(R.id.roll_no);
        esemester=(EditText)findViewById(R.id.semester);
        emob_no=(EditText)findViewById(R.id.mob_no);
        eothers=(EditText)findViewById(R.id.others);
        add=(Button)findViewById(R.id.addbtn);
        view=(Button)findViewById(R.id.viewbtn);
        viewall=(Button)findViewById(R.id.viewallbtn);
        delete=(Button)findViewById(R.id.deletebtn);
        Show1=(Button)findViewById(R.id.showbtn);
        modify=(Button)findViewById(R.id.modifybtn);

        modify.setEnabled(false);

        db=openOrCreateDatabase("Student_manage", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER,name VARCHAR,semester INTEGER,mobno INTEGER,others VARCHAR);");


        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0||
                        ename.getText().toString().trim().length()==0||
                        esemester.getText().toString().trim().length()==0||
                        emob_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO student VALUES('"+eroll_no.getText()+"','"+ename.getText()+"','"+esemester.getText()+"','"+emob_no.getText()+"','"+eothers.getText()+"');");
                showMessage("Success", "Record added successfully");
                clearText();
            }
        });
        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE rollno='"+eroll_no.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        modify.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+ename.getText()+"',semester='"+esemester.getText()+
                            "',mobno='"+emob_no.getText()+"',others='"+eothers.getText()+"' WHERE rollno='"+eroll_no.getText()+"'");
                    showMessage("Success", "Record Modified");
                    modify.setEnabled(false);
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
                StringBuffer buffer=new StringBuffer();
                if(c.moveToFirst())
                {
                    ename.setText(c.getString(1));
                    esemester.setText(c.getString(2));
                    emob_no.setText(c.getString(3));
                    eothers.setText(c.getString(4));
                    buffer.append("Rollno: "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Semester: "+c.getString(2)+"\n");
                    buffer.append("Mobno: "+c.getString(3)+"\n");
                    buffer.append("Others: "+c.getString(4)+"\n\n");
                    showMessage("Student Details", buffer.toString());
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
                modify.setEnabled(true);
            }
        });
        viewall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Cursor c=db.rawQuery("SELECT * FROM student", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Rollno: "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Semester: "+c.getString(2)+"\n");
                    buffer.append("Mobno: "+c.getString(3)+"\n");
                    buffer.append("Others: "+c.getString(4)+"\n\n");
                }
                showMessage("Student Details", buffer.toString());
            }
        });
        Show1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(StudentMainActivity.this, ScrollingActivity.class);
                StudentMainActivity.this.startActivity(mainIntent);
            }
        });

    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        eroll_no.setText("");
        ename.setText("");
        esemester.setText("");
        emob_no.setText("");
        eothers.setText("");
        eroll_no.requestFocus();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

}