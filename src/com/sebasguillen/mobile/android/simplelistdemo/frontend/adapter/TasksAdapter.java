package com.sebasguillen.mobile.android.simplelistdemo.frontend.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sebasguillen.mobile.android.simplelistdemo.R;
import com.sebasguillen.mobile.android.simplelistdemo.backend.sql.SQLiteHelper;
import com.sebasguillen.mobile.android.simplelistdemo.backend.stat.Consts;

/**
 * Adapter for tasks
 * Manages the task list views
 * @author Sebastian Guillen
 */
public class TasksAdapter extends CursorAdapter {

	public TasksAdapter(Context context, Cursor c, int flag) {
		super(context, c, flag);
	}

	@Override
	public void bindView(View view, Context context, Cursor c) {
		RowViewHolder holder = (RowViewHolder) view.getTag();
		if ( holder != null ) {
			int id = c.getInt(c.getColumnIndexOrThrow(SQLiteHelper._ID));
			String title = c.getString(c.getColumnIndexOrThrow(SQLiteHelper.TASK_COLUMN));
			String date = Consts.EMPTY_STRING;
			long dueDate = c.getLong(c.getColumnIndexOrThrow(SQLiteHelper.DATE_COLUMN));
			if (dueDate != 0){
				date += DateFormat.getDateFormat(context).format(dueDate);
				date += Consts.SINGLE_SPACE;
				date += DateFormat.getTimeFormat(context).format(dueDate);
			}
			String checked = c.getString(c.getColumnIndexOrThrow(SQLiteHelper.COMPLETED_COLUMN));

			holder.id = id;
			holder.textView.setText(title);
			holder.textView.setSingleLine(true);
			holder.dateView.setText(date);
			holder.dateView.setVisibility(View.GONE);
			holder.checkBox.setChecked(Boolean.valueOf(checked));
			holder.editText.setVisibility(View.GONE);
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(context, R.layout.listitem_task, null);

		CheckBox checkBox = (CheckBox) view.findViewById(R.id.task_CheckBox);
		TextView textView = (TextView) view.findViewById(R.id.task_text_textView);
		TextView dateView = (TextView) view.findViewById(R.id.task_date_textView);
		Button editText = (Button) view.findViewById(R.id.task_Edit_Button);
		boolean expanded = false;

		RowViewHolder holder = new RowViewHolder(checkBox, textView, dateView, editText, expanded, this);
		view.setTag(holder);
		return view;
	}

}