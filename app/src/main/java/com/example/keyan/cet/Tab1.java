package com.example.keyan.cet;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Keyan on 26/05/2017.
 */

public class Tab1 extends Fragment {

    int lastpressed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         final View rootView = inflater.inflate(R.layout.tab1, container, false);

//        Button randButton = (Button) rootView.findViewById(R.id.randButton);
//        Button resetButton = (Button) rootView.findViewById(R.id.resetButton);
//        Button refreshButton = (Button) rootView.findViewById(R.id.refreshButton);
        //final TextView textBox =  (TextView) rootView.findViewById(R.id.textBox);
        final LineChart mChart = (LineChart) rootView.findViewById(R.id.line_chart);
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

//        int milli = c.get(Calendar.MILLISECOND);

        mChart.getDescription().setEnabled(false);
        //mChart.getDescription().setText("Rainfall");
        //mChart.getDescription().setTextColor(android.R.color.holo_green_dark); doesnt work
        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        //mChart.setVisibleXRange(0,24); crashes


        final LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data

        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(mTfLight);
        l.setTextColor(Color.BLUE);
        l.setEnabled(false);



        XAxis xl = mChart.getXAxis();
        //xl.setTypeface(mTfLight);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(Color.BLUE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);




        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(Color.BLUE);
        //leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);



        try {

            FileInputStream fileInputStream = getActivity().openFileInput("ArduinoData.txt");
            InputStreamReader inputStreamReader = new InputStreamReader((fileInputStream));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                if (lines.charAt(0)=='R'){
                    stringBuffer.append(lines.substring(1,lines.length()) + "\n");


                if (data != null) {

                    ILineDataSet set = data.getDataSetByIndex(0);
                    // set.addEntry(...); // can be called as well

                    if (set == null) {
                        set = createSet();
                        data.addDataSet(set);
                    }

                    Float RandFloat = Float.parseFloat(lines.substring(1,lines.length()));


                    data.addEntry(new Entry(set.getEntryCount(), (float) (RandFloat)), 0);
                    //data.addEntry(new Entry(c.get(Calendar.MILLISECOND), (float) (RandFloat)), 0);
                    //Toast.makeText(getActivity().getApplicationContext(), c.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();

                    data.notifyDataChanged();

                    // let the chart know it's data has changed
                    mChart.notifyDataSetChanged();

                    // limit the number of visible entries
                    mChart.setVisibleXRangeMaximum(24);
                    // mChart.setVisibleYRange(30, AxisDependency.LEFT);
                    mChart.setVisibleXRange(0,24);

                    // move to the latest entry
                    mChart.moveViewToX(data.getEntryCount());
                }}

            }
            //textBox.setText(stringBuffer);
            fileInputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();

        }




//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // enable description text
//
//                try {
//
//                    FileInputStream fileInputStream = getActivity().openFileInput("Data1.txt");
//                    InputStreamReader inputStreamReader = new InputStreamReader((fileInputStream));
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    StringBuffer stringBuffer = new StringBuffer();
//
//
//                    String lines;
//                    while ((lines = bufferedReader.readLine()) != null) {
//
//                        stringBuffer.append(lines + "\n");
//                        if (data != null) {
//
//                            ILineDataSet set = data.getDataSetByIndex(0);
//                            // set.addEntry(...); // can be called as well
//
//                            if (set == null) {
//                                set = createSet();
//                                data.addDataSet(set);
//
//                            }
//
//                            Float RandFloat = Float.parseFloat(lines);
//
//                            data.addEntry(new Entry(set.getEntryCount(), (float) (RandFloat)), 0);
//                            data.notifyDataChanged();
//
//                            // let the chart know it's data has changed
//                            mChart.notifyDataSetChanged();
//
//                            // limit the number of visible entries
//                            //mChart.setVisibleXRangeMaximum(120);
//                            // mChart.setVisibleYRange(30, AxisDependency.LEFT);
//
//                            // move to the latest entry
//                            //mChart.moveViewToX(data.getEntryCount());
//                        }
//
//                    }
//                    textBox.setText(stringBuffer);
//
//                    fileInputStream.close();
//
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                }
//
//            }
//
//        });
//
//
//
//
//
//        randButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // enable description text
//
//
//
//                if (data != null) {
//
//                    ILineDataSet set = data.getDataSetByIndex(0);
//                    // set.addEntry(...); // can be called as well
//
//                    if (set == null) {
//                        set = createSet();
//                        data.addDataSet(set);
//
//                    }
//
//                    Float RandFloat = (float) Math.random() * 40;
//
//                    data.addEntry(new Entry(set.getEntryCount(), (float) (RandFloat)), 0);
//                    data.notifyDataChanged();
//
//                    // let the chart know it's data has changed
//                    mChart.notifyDataSetChanged();
//
//                    // limit the number of visible entries
//                    mChart.setVisibleXRangeMaximum(120);
//                    // mChart.setVisibleYRange(30, AxisDependency.LEFT);
//
//                    // move to the latest entry
//                    mChart.moveViewToX(data.getEntryCount());
//
//
//                    try {
//                        FileOutputStream fileOutputStream = getActivity().openFileOutput("ArduinoData.txt", MODE_APPEND);
//                        fileOutputStream.write(RandFloat.toString().getBytes());
//                        fileOutputStream.write("\n".getBytes());
//                        fileOutputStream.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    // this automatically refreshes the chart (calls invalidate())
//                    // mChart.moveViewTo(data.getXValCount()-7, 55f,
//                    // AxisDependency.LEFT);
//                }
//
//            }
//
//        });
//
//        resetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                try {
//                        FileOutputStream fileOutputStream = getActivity().openFileOutput("ArduinoData.txt", MODE_PRIVATE);
//                        //fileOutputStream.write("".getBytes());
//                        fileOutputStream.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    Toast.makeText(getActivity().getApplicationContext(), "Reset, switch to section 3 or beyond and back to refresh graph", Toast.LENGTH_SHORT).show();
//
//
//
//
//            }
//
//        });


        return rootView;
    }



    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        //set.setCircleColor(Color.WHITE);
        //set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setDrawFilled(true);
        //set.setFillColor(android.R.color.holo_red_light);

        //This sets the values being highlighted by tap gesture
        set.setHighlightEnabled(false);

        //set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);


        return set;
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
