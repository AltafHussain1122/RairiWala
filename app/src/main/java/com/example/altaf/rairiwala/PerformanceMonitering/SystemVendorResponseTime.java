package com.example.altaf.rairiwala.PerformanceMonitering;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.altaf.rairiwala.Singelton.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AltafHussain on 5/7/2018.
 */

public class SystemVendorResponseTime {
    private Context context;

    public SystemVendorResponseTime(Context context) {
        this.context = context;
    }


    public void addNewOrderPlacementTime(final int vendor_id, final int order_id, final String type) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        final String time = dateFormat.format(date);
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_ORDER_TIME,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error") == false) {


                                Toast.makeText(context, jsonObject.getString("message") + "", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, jsonObject.getString("message") + "", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("vendor_id", String.valueOf(vendor_id));
                params.put("order_id", String.valueOf(order_id));
                if (type.equals("placement")) {
                    params.put("type", "placement");
                } else if (type.equals("delivered")) {
                    params.put("type", "delivered");
                }

                params.put("time", time);
                return params;
            }

        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
