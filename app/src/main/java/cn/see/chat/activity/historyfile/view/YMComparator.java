package cn.see.chat.activity.historyfile.view;


import java.util.Comparator;

import cn.see.chat.entity.FileItem;

public class YMComparator implements Comparator<FileItem> {

	@Override
	public int compare(FileItem o1, FileItem o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

}
