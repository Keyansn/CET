package com.example.keyan.cet;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String DEVICE_ADDRESS = "00:14:03:06:21:FA"; //Unique to Bluetooth Module
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    EditText editText;
    TextView textView,txtArduino;
    Button startButton, sendButton,clearButton,stopButton,resetButton;

    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    private LineChart mChart;
    private Boolean fabChecked = TRUE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doTheAutoRefresh();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fabChecked){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //Toast.makeText(getApplicationContext(), "Pressed Switch", Toast.LENGTH_SHORT).show();
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop_white_48dp, getApplicationContext().getTheme()));
                        fabChecked = FALSE;
                    }
                    onClickStart();

                }else {
                    onClickClear();
                    try {
                        onClickStop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_SHORT).show();
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_48dp, getApplicationContext().getTheme()));
                        fabChecked = TRUE;
                    }
                }





                try {

                    FileInputStream fileInputStream = openFileInput("ArduinoData.txt");
                    InputStreamReader inputStreamReader = new InputStreamReader((fileInputStream));
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer = new StringBuffer();

                    String lines;
                    while ((lines = bufferedReader.readLine()) != null) {

                        stringBuffer.append(lines + "\n");

            }
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                refresher();


            }
        });


        resetButton = (Button) findViewById(R.id.resetButton);

         mChart = (LineChart) findViewById(R.id.line_chart);

        //setUiEnabled(false);

//        editText = (EditText) findViewById(R.id.editText);
//
    }
//        edittext.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
////                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
////                    // Perform action on key press
//                    Toast.makeText(getApplicationContext(), "Pressed Enter", Toast.LENGTH_LONG).show();
//                    String Mytextmessage = editText.getText().toString();
//                    try {
//                        FileOutputStream fileOutputStream = openFileOutput("myText.csv", MODE_APPEND);
//                        fileOutputStream.write(Mytextmessage.getBytes());
//                        fileOutputStream.close();
//                        Toast.makeText(getApplicationContext(), "Text saved", Toast.LENGTH_LONG).show();
//                        editText.setText("");
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
////
//                    LineData data = mChart.getData();
//
//                    if (data != null) {
//
//                        ILineDataSet set = data.getDataSetByIndex(0);
//                        // set.addEntry(...); // can be called as well
//
//                        if (set == null) {
//                            set = createSet();
//                            data.addDataSet(set);
//                        }
//
//                        data.addEntry(new Entry(set.getEntryCount(), (float) (Float.valueOf(Mytextmessage))), 0);
//                        data.notifyDataChanged();
//
//                        // let the chart know it's data has changed
//                        mChart.notifyDataSetChanged();
//
//                        // limit the number of visible entries
//                        mChart.setVisibleXRangeMaximum(120);
//                        // mChart.setVisibleYRange(30, AxisDependency.LEFT);
//
//                        // move to the latest entry
//                        mChart.moveViewToX(data.getEntryCount());
//
//                        // this automatically refreshes the chart (calls invalidate())
//                        // mChart.moveViewTo(data.getXValCount()-7, 55f,
//                        // AxisDependency.LEFT);
//                    }
//                    String Comma = ",";
//
//                    try {
//                        FileOutputStream fileOutputStream = openFileOutput("myText.csv", MODE_APPEND);
//                        fileOutputStream.write(Comma.getBytes());
//                        fileOutputStream.close();
//                        Toast.makeText(getApplicationContext(), "Text saved",Toast.LENGTH_LONG).show();
//                        editText.setText("");
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//        textView = (TextView) findViewById(R.id.textView);
//        mChart = (LineChart) findViewById(R.id.line_chart);
//
//        //mChart.setOnChartValueSelectedListener(this);
//
//        // enable description text
//
//        mChart.getDescription().setEnabled(true);
//        mChart.getDescription().setText("Rainfall blah blah blaaah");
//        //mChart.getDescription().setTextColor(android.R.color.holo_green_dark); doesnt work
//
//
//        // enable touch gestures
//        mChart.setTouchEnabled(true);
//
//        // enable scaling and dragging
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);
//        mChart.setDrawGridBackground(false);
//
//        // if disabled, scaling can be done on x- and y-axis separately
//        mChart.setPinchZoom(true);
//
//        // set an alternative background color
//        mChart.setBackgroundColor(Color.WHITE);
//
//        LineData data = new LineData();
//        data.setValueTextColor(Color.WHITE);
//
//        // add empty data
//        mChart.setData(data);
//
//        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        // modify the legend ...
//        l.setForm(Legend.LegendForm.LINE);
//        //l.setTypeface(mTfLight);
//        l.setTextColor(Color.BLUE);
//
//
//        XAxis xl = mChart.getXAxis();
//        //xl.setTypeface(mTfLight);
//        xl.setTextColor(Color.BLUE);
//        xl.setDrawGridLines(false);
//        xl.setAvoidFirstLastClipping(true);
//        xl.setEnabled(true);
//
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        //leftAxis.setTypeface(mTfLight);
//        leftAxis.setTextColor(Color.BLUE);
//        //leftAxis.setAxisMaximum(100f);
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setDrawGridLines(true);
//
//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setEnabled(false);
//
//}


            private LineDataSet createSet() {

                LineDataSet set = new LineDataSet(null, "Dynamic Data");
                set.setAxisDependency(YAxis.AxisDependency.LEFT);
                set.setColor(ColorTemplate.getHoloBlue());
                //set.setCircleColor(Color.WHITE);
                //set.setLineWidth(2f);
                set.setDrawCircles(false);
                set.setCircleRadius(4f);
                set.setFillAlpha(65);
                set.setFillColor(ColorTemplate.getHoloBlue());


                //This sets the values being highlighted by tap gesture
                set.setHighlightEnabled(false);

                //set.setHighLightColor(Color.rgb(244, 117, 117));
                set.setValueTextColor(Color.WHITE);
                set.setValueTextSize(9f);
                set.setDrawValues(false);


                return set;
            }

    public void setUiEnabled(boolean bool) {
        startButton.setEnabled(!bool);
        sendButton.setEnabled(bool);
        stopButton.setEnabled(bool);
        txtArduino.setEnabled(bool);

    }

    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BTconnect()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }

    public void onClickStart() {
        if(BTinit())
        {
            if(BTconnect())
            {
                //setUiEnabled(true);
                deviceConnected=true;
                beginListenForData();
                //txtArduino.append("\nConnection Opened!\n");
                Toast.makeText(getApplicationContext(), "Connected",Toast.LENGTH_SHORT).show();

                onClickSend();

            }

        }
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                    //Toast.makeText(getApplicationContext(), string ,Toast.LENGTH_SHORT).show();


                                    //txtArduino.append(string);///////////////////////////////////////////Insert code to log data here///////////////////////////////////////////////////////////////////////////////////
                                    //Toast.makeText(getApplicationContext(), string,Toast.LENGTH_SHORT).show();


                                    try {
                                        FileOutputStream fileOutputStream = openFileOutput("ArduinoData.txt", MODE_APPEND);
                                        fileOutputStream.write(string.getBytes());
                                        fileOutputStream.close();
                                        //Toast.makeText(getApplicationContext(), string ,Toast.LENGTH_SHORT).show();
                                        //refresher();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }

    public void onClickSend() {
//        String string = editText.getText().toString();
//        string.concat("\n");
        try {
            outputStream.write("1".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //txtArduino.append("\nSent Data:"+string+"\n");

    }

    public void onClickStop() throws IOException {
        stopThread = true;
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        socket.close();
        //setUiEnabled(false);
        deviceConnected=false;
        //txtArduino.append("\nConnection Closed!\n");
    }

    public void onClickClear() {
        try {
            outputStream.write("0".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void random(View view){
        Toast.makeText(getApplicationContext(), "Random Value Added",Toast.LENGTH_SHORT).show();
        //lineChart = (LineChart) findViewById(R.id.line_chart);
        String Mytextmessage = editText.getText().toString();
        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(120);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    public void fromArduino(String theText){
        //Toast.makeText(getApplicationContext(), "#GetMerty2017",Toast.LENGTH_SHORT).show();
        //lineChart = (LineChart) findViewById(R.id.line_chart);
        //String Mytextmessage = editText.getText().toString();
        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) (Float.valueOf(theText))), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(120);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }
    private Menu menu;
    private Boolean pressed =Boolean.TRUE;



    @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                this.menu = menu;
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    refresher();
                    return true;
                }

                if(id == R.id.myswitch){

                    Switch menuswitch = (Switch) findViewById(R.id.myswitch);

                    if(menuswitch.isChecked()){
                        Toast.makeText(getApplicationContext(), "Pregssed Switch", Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getApplicationContext(), "Pressed Switch", Toast.LENGTH_LONG).show();
                    return true;
                }

                if ((id == R.id.onClickStart)&&pressed) {
                    Toast.makeText(getApplicationContext(), "Pressed Start", Toast.LENGTH_LONG).show();
                    onClickStart();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_stop_white_48dp, getApplicationContext().getTheme()));
                    }
                    pressed = FALSE;
                    return true;
                }

                if ((id == R.id.onClickStart)&!pressed) {
                    Toast.makeText(getApplicationContext(), "Pressed Stop", Toast.LENGTH_LONG).show();
                    onClickClear();
                    try {
                        onClickStop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_play_arrow_white_48dp, getApplicationContext().getTheme()));
                    }
                    pressed = TRUE;
                    return true;
                }

                if (id == R.id.onClickStop) {
                    Toast.makeText(getApplicationContext(), "Pressed Stop", Toast.LENGTH_LONG).show();
                    try {
                        onClickStop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                if (id == R.id.onClickClear) {
                    Toast.makeText(getApplicationContext(), "Pressed Clear", Toast.LENGTH_LONG).show();
                try {
                        FileOutputStream fileOutputStream = openFileOutput("ArduinoData.txt", MODE_PRIVATE);
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();}
                    return true;
                }

                if (id == R.id.onClickSend) {
                    Toast.makeText(getApplicationContext(), "Pressed Send", Toast.LENGTH_LONG).show();
                    onClickSend();
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }



            public void refresher(){
                mSectionsPagerAdapter.notifyDataSetChanged();
            }

private  final Handler handler = new Handler();

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Write code for your refresh logic
                refresher();

               doTheAutoRefresh();
            }
        }, 5000);   //this is for 5mins do 3600000 for hourly
    }






//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.tab1, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }

            /**
             * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
             * one of the sections/tabs/pages.
             */




            public class SectionsPagerAdapter extends FragmentPagerAdapter {

                public SectionsPagerAdapter(FragmentManager fm) {
                    super(fm);
                }

                @Override
                public Fragment getItem(int position) {
                    // getItem is called to instantiate the fragment for the given page.
                    // Return a PlaceholderFragment (defined as a static inner class below).
                    //return PlaceholderFragment.newInstance(position + 1);

                    switch (position) {
                        case 0:
                            Tab6 tab6 = new Tab6();
                            return tab6;
                        case 1:
                            Tab1 tab1 = new Tab1();
                            return tab1;
                        case 2:
                            Tab2 tab2 = new Tab2();
                            return tab2;
                        case 3:
                            Tab3 tab3 = new Tab3();
                            return tab3;
                        case 4:
                            Tab4 tab4 = new Tab4();
                            return tab4;
                        case 5:
                            Tab5 tab5 = new Tab5();
                            return tab5;
                        default:
                            return null;
                    }


                }

                public int getItemPosition(Object object) {
                    return POSITION_NONE;
                }

                @Override
                public int getCount() {
                    // Show 5 total pages.
                    return 6;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return "Home";
                        case 1:
                            return getString(R.string.SECTION_1);
                        case 2:
                            return getString(R.string.SECTION_2);
                        case 3:
                            return getString(R.string.SECTION_3);
                        case 4:
                            return getString(R.string.SECTION_4);
                        case 5:
                            return getString(R.string.SECTION_5);
                    }
                    return null;
                }


            }



        }