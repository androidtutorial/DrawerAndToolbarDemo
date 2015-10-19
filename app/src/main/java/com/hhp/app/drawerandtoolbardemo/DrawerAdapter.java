package com.hhp.app.drawerandtoolbardemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DrawerAdapter extends ArrayAdapter<DrawerItem> {
	
	private List<DrawerItem> mDrawerItems;
	private LayoutInflater mInflater;

	public DrawerAdapter(Context context, int layoutResource, List<DrawerItem> items) {
		super(context, layoutResource,items);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDrawerItems = items;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.navigation_item_row, parent, false);

			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
			holder.title = (TextView) convertView.findViewById(R.id.textViewName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		DrawerItem item = mDrawerItems.get(position);

		holder.icon.setImageResource(item.getIcon());
		holder.title.setText(item.getTitle());
		
		return convertView;
	}
	
	private static class ViewHolder {
		public ImageView icon;
		public TextView title;
	}
}
