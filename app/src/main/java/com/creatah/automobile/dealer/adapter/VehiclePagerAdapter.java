package com.creatah.automobile.dealer.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Vehicle;

import java.util.List;


public class VehiclePagerAdapter extends PagerAdapter {

    private TextView stateEditText;
    private TextView areaEditText;
    private TextView serialEditText;
    private TextView serialNoEditText;

    private TextView makeTextView;
    private TextView modelTextView;
    private TextView fuelTypeTextView;

    private LinearLayout vehicleLayout;

    private List<Vehicle> vehicleList;

    private Context context;

    private LayoutInflater inflater;

    private VehicleSelectListener selectListener;

    public VehiclePagerAdapter(Context context, List<Vehicle> vehicleList, VehicleSelectListener selectListener) {
        this.context = context;
        this.vehicleList = vehicleList;
        this.selectListener = selectListener;
    }

    @Override
    public int getCount() {
        return vehicleList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.adapter_vehicle_list, container, false);

        stateEditText = (TextView) itemView.findViewById(R.id.stateCode);
        areaEditText = (TextView) itemView.findViewById(R.id.areaCode);
        serialEditText = (TextView) itemView.findViewById(R.id.serialText);
        serialNoEditText = (TextView) itemView.findViewById(R.id.serialNo);

        makeTextView = (TextView) itemView.findViewById(R.id.makeTextView);
        modelTextView = (TextView) itemView.findViewById(R.id.modalTextView);
        fuelTypeTextView = (TextView) itemView.findViewById(R.id.fuelTypeTextView);
        vehicleLayout = (LinearLayout) itemView.findViewById(R.id.vehicleLayout);



          final Vehicle vehicle = vehicleList.get(position);

          if (vehicle.getVehicleNo() != null) {

              if (vehicle.getVehicleNo().length() > 3) {
                String[] vehicleNo = vehicle.getVehicleNo().split("/");
                stateEditText.setText(vehicleNo[0]);
                areaEditText.setText(vehicleNo[1]);
                serialEditText.setText(vehicleNo[2]);
                serialNoEditText.setText(vehicleNo[3]);
              }
          }

        makeTextView.setText(vehicle.getMake());
        modelTextView.setText(vehicle.getModel());
        fuelTypeTextView.setText(vehicle.getFuelType());

        if (vehicle.isSelected()) {
            vehicleLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.buttonprimarybgselect1));
        } else {
            vehicleLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.buttonbggray));
        }

        vehicleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < vehicleList.size(); i++) {
                    vehicleList.get(i).setSelected(false);
                }
                vehicle.setSelected(true);
                selectListener.onSelectVehicle(vehicle, position);
            }
        });

        ((ViewPager) container).addView(itemView);

        return itemView;
    }


    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public interface VehicleSelectListener {
        void onSelectVehicle(Vehicle vehicle, int position);
    }
}