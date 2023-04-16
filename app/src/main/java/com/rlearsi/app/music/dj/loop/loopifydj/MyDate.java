package com.rlearsi.app.music.dj.loop.loopifydj;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyDate {

    private Context context;
    private InterfaceUpdates interfaceUpdates;
    public static int date = Integer.parseInt(MyDate.dateFormat(new Date(), "date_int"));
    private int myear, mmonth, mday, mhour, mminute;

    public MyDate(Context context, InterfaceUpdates interfaceUpdates) {
        this.context = context;
        this.interfaceUpdates = interfaceUpdates;
    }

    public String dateIntToFormat(String dateX) {

        Date date = new Date(dateToMills(dateX));

        return dateFormat(date, "day_months");

    }

    //Converte apenas datas no formato new Date()
    public static String dateFormat(Date date, String date_or_time) {

        SimpleDateFormat formatter = null;

        switch (date_or_time) {
            case "date":

                formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                break;
            case "date_pt":

                formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

                break;
            case "time":

                formatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

                break;
            case "datetime":

                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

                break;
            case "date_int":

                formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

                break;
            case "time_int":

                formatter = new SimpleDateFormat("HHmm", Locale.ENGLISH);

                break;
            case "datetime_int":

                formatter = new SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH);

                break;

            case "day_months":

                formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());

                break;

            case "day_month":

                formatter = new SimpleDateFormat("dd/MMM yyyy", Locale.getDefault());

                break;
        }

        return formatter != null ? formatter.format(date) : "0";

    }

    public static long dateToMills(String dateString){

        //Specifying the pattern of input date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm", Locale.ENGLISH);
        long new_date = 0;
        //String dateString = "22-03-2017 11:18:32";
        try{
            //formatting the dateString to convert it into a Date
            Date date = sdf.parse(dateString);
            if (date != null) {

                new_date = date.getTime();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

            }

        }catch(ParseException e){
            //e.printStackTrace();
        }

        return new_date;

    }

    public static long days(String startDate, String endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        try {

            Date date1 = sdf.parse(startDate);
            Date date2 = sdf.parse(endDate);

            if (date2 != null && date1 != null) {

                long diff = date2.getTime() - date1.getTime();

                return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            } else {

                return 1;

            }

        } catch (ParseException e) {

            return 1;

        }

    }

    private void initDateData(String lastDate) {

        if (lastDate.length() < 8) {

            Calendar c = Calendar.getInstance();
            this.myear = c.get(Calendar.YEAR);
            this.mmonth = c.get(Calendar.MONTH);
            this.mday = c.get(Calendar.DAY_OF_MONTH);
            this.mhour = c.get(Calendar.HOUR_OF_DAY);
            this.mminute = c.get(Calendar.MINUTE);

        } else {

            this.myear = Integer.parseInt(lastDate.substring(0, 4));
            this.mmonth = Integer.parseInt(lastDate.substring(4, 6)) - 1;
            this.mday = Integer.parseInt(lastDate.substring(6, 8));

        }

    }

    /*public void setStartDate(String lastDate) {

        initDateData(lastDate);

        android.app.DatePickerDialog.OnDateSetListener dateSetListener = (view, selectYear, selectMonth, dayOfMonth) -> {

            this.mmonth = selectMonth;
            this.myear = selectYear;
            this.mday = dayOfMonth;

            String start_date = this.myear + "" +
                    ((this.mmonth + 1) < 10 ? "0" + (this.mmonth + 1) : (this.mmonth + 1)) + "" +
                    (this.mday < 10 ? "0" + this.mday : this.mday);

            //Envia a resposta para a interface
            interfaceUpdates.handleItems(0, 0, 0, start_date, CALENDAR_INTERFACE);

        };

        android.app.DatePickerDialog date = new android.app.DatePickerDialog(context, dateSetListener, myear, mmonth, mday);
        date.setButton(BUTTON_POSITIVE, context.getString(android.R.string.ok), date);
        date.setButton(BUTTON_NEGATIVE, context.getString(android.R.string.cancel), date);
        date.show();

    }*/

}