package cn.nhorizon.android.demo.todo.fragment;

import java.util.ArrayList;

import cn.nhorizon.android.demo.todo.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.ArrayAdapter;

public class TodoFragmentActivity extends FragmentActivity implements OnNewItemAddedListener{
	private ArrayAdapter<String> aa;
	private ArrayList<String> todoItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.framgent_main);
		
		FragmentManager fm = getSupportFragmentManager();
		
		ToDoListFragment toDoListFragment = (ToDoListFragment) fm.findFragmentById(R.id.TodoListFragment);
		
		todoItems = new ArrayList<String>();
		aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,todoItems);
		
		toDoListFragment.setListAdapter(aa);
		
	}

	@Override
	public void onNewItemAdded(String newItem) {
		todoItems.add(newItem);
		aa.notifyDataSetChanged();
	}

}
