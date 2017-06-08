package com.example.keyan.cet;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static com.example.keyan.cet.R.color.colorPrimary;


/**
 * Created by Keyan on 26/05/2017.
 */

public class Tab3 extends Fragment {

    int lastpressed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        final TextView OneD = (TextView) rootView.findViewById(R.id.textView2);
        final TextView OneM = (TextView) rootView.findViewById(R.id.textView3);
        final TextView ThreeM = (TextView) rootView.findViewById(R.id.textView4);
        final TextView SixM = (TextView) rootView.findViewById(R.id.textView5);
        final TextView OneY = (TextView) rootView.findViewById(R.id.textView6);
        final TextView All = (TextView) rootView.findViewById(R.id.textView7);

        OneD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastpressed = 1;
                one_day(OneD,OneM,ThreeM,SixM,OneY,All);
                Toast.makeText(getActivity().getApplicationContext(), "One Day", Toast.LENGTH_SHORT).show();
            }
        });

        OneM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastpressed = 2;
                one_month(OneD,OneM,ThreeM,SixM,OneY,All);
                Toast.makeText(getActivity().getApplicationContext(), "One Month", Toast.LENGTH_SHORT).show();
            }
        });

        ThreeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastpressed = 3;
                three_month(OneD,OneM,ThreeM,SixM,OneY,All);
                Toast.makeText(getActivity().getApplicationContext(), "Three Months", Toast.LENGTH_SHORT).show();
            }
        });

        SixM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastpressed = 4;
                six_month(OneD,OneM,ThreeM,SixM,OneY,All);
                Toast.makeText(getActivity().getApplicationContext(), "Six Months", Toast.LENGTH_SHORT).show();
            }
        });

        OneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastpressed = 5;
                one_year(OneD,OneM,ThreeM,SixM,OneY,All);
                Toast.makeText(getActivity().getApplicationContext(), "One Year", Toast.LENGTH_SHORT).show();
            }
        });

        All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastpressed = 6;
                all_time(OneD,OneM,ThreeM,SixM,OneY,All);
                Toast.makeText(getActivity().getApplicationContext(), "All", Toast.LENGTH_SHORT).show();
            }
        });

        switch (lastpressed) {
            case 1:  one_day(OneD,OneM,ThreeM,SixM,OneY,All);
                break;
            case 2:  one_month(OneD,OneM,ThreeM,SixM,OneY,All);
                break;
            case 3:  three_month(OneD,OneM,ThreeM,SixM,OneY,All);
                break;
            case 4:  six_month(OneD,OneM,ThreeM,SixM,OneY,All);
                break;
            case 5:  one_year(OneD,OneM,ThreeM,SixM,OneY,All);
                break;
            case 6:  all_time(OneD,OneM,ThreeM,SixM,OneY,All);
                break;
            default:
                break;
        }


        return rootView;
    }

    public void one_day(TextView OneD,TextView OneM,TextView ThreeM,TextView SixM,TextView OneY,TextView All) {

        OneD.setTextColor(Color.parseColor("#FF4081"));
        OneD.setPaintFlags(OneD.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        OneM.setTextColor(Color.parseColor("#808080"));
        OneM.setPaintFlags(View.INVISIBLE);
        ThreeM.setTextColor(Color.parseColor("#808080"));
        ThreeM.setPaintFlags(View.INVISIBLE);
        SixM.setTextColor(Color.parseColor("#808080"));
        SixM.setPaintFlags(View.INVISIBLE);
        OneY.setTextColor(Color.parseColor("#808080"));
        OneY.setPaintFlags(View.INVISIBLE);
        All.setTextColor(Color.parseColor("#808080"));
        All.setPaintFlags(View.INVISIBLE);
    }

    public void one_month(TextView OneD,TextView OneM,TextView ThreeM,TextView SixM,TextView OneY,TextView All){
        OneM.setTextColor(Color.parseColor("#FF4081"));
        OneM.setPaintFlags(OneD.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        OneD.setTextColor(Color.parseColor("#808080"));
        OneD.setPaintFlags(View.INVISIBLE);
        ThreeM.setTextColor(Color.parseColor("#808080"));
        ThreeM.setPaintFlags(View.INVISIBLE);
        SixM.setTextColor(Color.parseColor("#808080"));
        SixM.setPaintFlags(View.INVISIBLE);
        OneY.setTextColor(Color.parseColor("#808080"));
        OneY.setPaintFlags(View.INVISIBLE);
        All.setTextColor(Color.parseColor("#808080"));
        All.setPaintFlags(View.INVISIBLE);
    }

    public void three_month(TextView OneD,TextView OneM,TextView ThreeM,TextView SixM,TextView OneY,TextView All){
        ThreeM.setTextColor(Color.parseColor("#FF4081"));
        ThreeM.setPaintFlags(OneD.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        OneD.setTextColor(Color.parseColor("#808080"));
        OneD.setPaintFlags(View.INVISIBLE);
        OneM.setTextColor(Color.parseColor("#808080"));
        OneM.setPaintFlags(View.INVISIBLE);
        SixM.setTextColor(Color.parseColor("#808080"));
        SixM.setPaintFlags(View.INVISIBLE);
        OneY.setTextColor(Color.parseColor("#808080"));
        OneY.setPaintFlags(View.INVISIBLE);
        All.setTextColor(Color.parseColor("#808080"));
        All.setPaintFlags(View.INVISIBLE);
    }

    public void six_month(TextView OneD,TextView OneM,TextView ThreeM,TextView SixM,TextView OneY,TextView All){
        SixM.setTextColor(Color.parseColor("#FF4081"));
        SixM.setPaintFlags(OneD.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        OneD.setTextColor(Color.parseColor("#808080"));
        OneD.setPaintFlags(View.INVISIBLE);
        ThreeM.setTextColor(Color.parseColor("#808080"));
        ThreeM.setPaintFlags(View.INVISIBLE);
        OneM.setTextColor(Color.parseColor("#808080"));
        OneM.setPaintFlags(View.INVISIBLE);
        OneY.setTextColor(Color.parseColor("#808080"));
        OneY.setPaintFlags(View.INVISIBLE);
        All.setTextColor(Color.parseColor("#808080"));
        All.setPaintFlags(View.INVISIBLE);
    }

    public void one_year(TextView OneD,TextView OneM,TextView ThreeM,TextView SixM,TextView OneY,TextView All){
        OneY.setTextColor(Color.parseColor("#FF4081"));
        OneY.setPaintFlags(OneD.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        OneD.setTextColor(Color.parseColor("#808080"));
        OneD.setPaintFlags(View.INVISIBLE);
        ThreeM.setTextColor(Color.parseColor("#808080"));
        ThreeM.setPaintFlags(View.INVISIBLE);
        SixM.setTextColor(Color.parseColor("#808080"));
        SixM.setPaintFlags(View.INVISIBLE);
        OneM.setTextColor(Color.parseColor("#808080"));
        OneM.setPaintFlags(View.INVISIBLE);
        All.setTextColor(Color.parseColor("#808080"));
        All.setPaintFlags(View.INVISIBLE);
    }

    public void all_time(TextView OneD,TextView OneM,TextView ThreeM,TextView SixM,TextView OneY,TextView All){
        All.setTextColor(Color.parseColor("#FF4081"));
        All.setPaintFlags(OneD.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        OneD.setTextColor(Color.parseColor("#808080"));
        OneD.setPaintFlags(View.INVISIBLE);
        ThreeM.setTextColor(Color.parseColor("#808080"));
        ThreeM.setPaintFlags(View.INVISIBLE);
        SixM.setTextColor(Color.parseColor("#808080"));
        SixM.setPaintFlags(View.INVISIBLE);
        OneY.setTextColor(Color.parseColor("#808080"));
        OneY.setPaintFlags(View.INVISIBLE);
        OneM.setTextColor(Color.parseColor("#808080"));
        OneM.setPaintFlags(View.INVISIBLE);
    }
}
