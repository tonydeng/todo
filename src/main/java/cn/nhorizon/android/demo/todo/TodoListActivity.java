package cn.nhorizon.android.demo.todo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import cn.nhorizon.android.demo.todo.utils.TodoDBAdapter;

public class TodoListActivity extends Activity {
	private static ArrayList<HashMap<String, String>> todoData = new ArrayList<HashMap<String,String>>();
	private static TodoDBAdapter db;
	private Menu mMenu;
	private MyAdapter adapter;
	private Cursor the_cursor;
	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_main);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		db = new TodoDBAdapter(this);
		db.open();
		
		the_cursor = db.fetchAllTasks();
		adapter = new MyAdapter(this, the_cursor);
		
		((Button)findViewById(R.id.b_add)).setOnClickListener(mAddToDoListener);
		((ListView)findViewById(R.id.list_todo)).setAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
		db.close();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		this.finish();
		db.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				cn.nhorizon.android.demo.todo.R.menu.todo_menu, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("item id", ""+item.getItemId());
        switch (item.getItemId()) {
            case R.id.clear_finished_tasks:
            	Log.d("is clear_finished_tasks", ""+R.id.clear_finished_tasks);
            	db.deleteCompleteTasks();
            	the_cursor = db.fetchAllTasks();
            	adapter.changeCursor(the_cursor);
                return true;

        }   
        return false;
	}
	
	
	
	OnClickListener mAddToDoListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			final EditText task = (EditText) findViewById(R.id.editText1);
			db.createTask(task.getText().toString(), 0);
			the_cursor = db.fetchAllTasks();
			adapter.changeCursor(the_cursor);
		}
	};
	private class MyAdapter extends ResourceCursorAdapter{

		public MyAdapter(Context context,Cursor c) {
			super(context, R.layout.todo_row, c);
		}
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.todo_row, parent, false);
		}
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final CheckBox cb = (CheckBox) view.findViewById(R.id.task);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(cb.isChecked())
						db.updateTask((String) cb.getText(), 1);
					else
						db.updateTask((String) cb.getText(), 0);
				}
			});
			cb.setText(cursor.getString(cursor.getColumnIndex(TodoDBAdapter.KEY_TASK)));
			cb.setChecked(cursor.getInt(cursor.getColumnIndex(TodoDBAdapter.KEY_STATE)) == 0 ? false : true);
		}
		
	}
}
