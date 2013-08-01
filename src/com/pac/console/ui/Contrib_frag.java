package com.pac.console.ui;

import in.uncod.android.bypass.Bypass;

import com.pac.console.R;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Contrib_frag extends Fragment {
	
	
	public static Contrib_frag newInstance(String content) {
		Contrib_frag fragment = new Contrib_frag();		
		return fragment;
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle oflove) {
		
		View layout = inflater.inflate(R.layout.contrib_frag_layout, null);

		TextView contrib = (TextView) layout.findViewById(R.id.textView1);
		
		Bypass bypass = new Bypass();
		String markdownString = "#NATIVE MARKUP\n"
				+ "MUTHERFUCKERS!\n"
				+ "#Header sizes\n##Smaller header\n"
				+ "###Even smaller header\n"
				+ "Paragraphs are obviously supported along with all the fancy text styling you could want.\n"
				+ "There is *italic*, **bold** and ***bold italic***. Even links are supported, visit the\n"
				+ "github page for Bypass [here](https://github.com/Uncodin/bypass).\n"
				+ "* Nested List\n"
				+ "	* One\n"
				+ "	* Two\n"
				+ "	* Three\n"
				+ "* One\n"
				+ "	* One\n"
				+ "	* Two\n"
				+ "	* Three\n"
				+ "## Code Block Support\n"
				+ "	const char* str;\n"
				+ "str = env->GetStringUTFChars(markdown, NULL);";
		CharSequence string = bypass.markdownToSpannable(markdownString);
		contrib.setText(string);
		contrib.setMovementMethod(LinkMovementMethod.getInstance());
		
		return layout;
	}

}
