package com.azizulhakim.todotogether;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rumi on 11/15/2016.
 */

public class AboutPage extends InterfaceActivity {
    Button credits;
    TextView details;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);
        credits=(Button)findViewById(R.id.credits);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details=(TextView)findViewById(R.id.details);
                Spanned text = Html.fromHtml("<b>" + "Azizul Hakim" + "</b>"+"<br />"+"<small>"+"Dept of Computer Science and Engineering"+"<br/>"+"University of Dhaka"+"</small>"+"<br/>"+"<br/>"+"<b>" + "Ariful Amin" + "</b>"+"<br />"+"<small>"+"Dept of Computer Science and Engineering"+"<br/>"+"University of Dhaka"+"</small>"+"<br/>"+"<br/>"+"<b>" + "Abdur Rouf" + "</b>"+"<br />"+"<small>"+"Dept of Computer Science and Engineering"+"<br/>"+"University of Dhaka"+"</small>"+"<br/>"+"<br/>"+"<b>" + "\nMd. Abul Hasan Osama" + "</b>"+"<br />"+"<small>"+"Dept of Computer Science and Engineering"+"<br/>"+"University of Dhaka"+"</small>");
                details.setText(text);

            }
        });
    }
}
