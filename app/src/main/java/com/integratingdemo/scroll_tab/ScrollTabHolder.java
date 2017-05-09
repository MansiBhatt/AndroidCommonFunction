package com.integratingdemo.scroll_tab;

import android.widget.AbsListView;

interface ScrollTabHolder {

	void adjustScroll(int scrollHeight);

	void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}
