package com.creatah.automobile.dealer.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Vehicle;

import java.util.List;

/**
 * Created by santhana on 02/06/18.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private Context context;

    private List<Vehicle> vehicleList;

    private VehicleSelectListener selectListener;


    public VehicleAdapter(Context context, List<Vehicle> vehicleList, VehicleSelectListener listener) {
        this.context = context;
        this.vehicleList = vehicleList;
        this.selectListener = listener;
    }

    @Override
    public VehicleAdapter.VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vehicle_list, parent, false);
        VehicleViewHolder viewHolder = new VehicleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VehicleAdapter.VehicleViewHolder holder, final int position) {
        final Vehicle vehicle = vehicleList.get(position);

        if (vehicle.getVehicleNo() != null) {
            if (vehicle.getVehicleNo().length() > 3) {
                String[] vehicleNo = vehicle.getVehicleNo().split("/");
                holder.stateEditText.setText(vehicleNo[0]);
                holder.areaEditText.setText(vehicleNo[1]);
                holder.serialEditText.setText(vehicleNo[2]);
                holder.serialNoEditText.setText(vehicleNo[3]);
            }
        }

        holder.makeTextView.setText(vehicle.getMake());
        holder.modelTextView.setText(vehicle.getModel());
        holder.fuelTypeTextView.setText(vehicle.getFuelType());

        if (vehicle.isSelected()) {
            holder.vehicleLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.ripple_color));
        } else {
            holder.vehicleLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.vehicleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < vehicleList.size(); i++) {
                    vehicleList.get(i).setSelected(false);
                }
                vehicle.setSelected(true);
                selectListener.onSelectVehicle(vehicle);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {

        private TextView stateEditText;
        private TextView areaEditText;
        private TextView serialEditText;
        private TextView serialNoEditText;

        private TextView makeTextView;
        private TextView modelTextView;
        private TextView fuelTypeTextView;

        private LinearLayout vehicleLayout;


        public VehicleViewHolder(View itemView) {
            super(itemView);
            stateEditText = (TextView) itemView.findViewById(R.id.stateCode);
            areaEditText = (TextView) itemView.findViewById(R.id.areaCode);
            serialEditText = (TextView) itemView.findViewById(R.id.serialText);
            serialNoEditText = (TextView) itemView.findViewById(R.id.serialNo);

            makeTextView = (TextView) itemView.findViewById(R.id.makeTextView);
            modelTextView = (TextView) itemView.findViewById(R.id.modalTextView);
            fuelTypeTextView = (TextView) itemView.findViewById(R.id.fuelTypeTextView);

            vehicleLayout = (LinearLayout) itemView.findViewById(R.id.vehicleLayout);

        }
    }


    public interface VehicleSelectListener {
        void onSelectVehicle(Vehicle vehicle);
    }
}
