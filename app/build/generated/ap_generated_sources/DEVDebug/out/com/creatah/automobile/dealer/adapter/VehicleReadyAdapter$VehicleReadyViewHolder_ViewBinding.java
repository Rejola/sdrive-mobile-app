// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VehicleReadyAdapter$VehicleReadyViewHolder_ViewBinding implements Unbinder {
  private VehicleReadyAdapter.VehicleReadyViewHolder target;

  @UiThread
  public VehicleReadyAdapter$VehicleReadyViewHolder_ViewBinding(VehicleReadyAdapter.VehicleReadyViewHolder target,
      View source) {
    this.target = target;

    target.lineView = Utils.findRequiredView(source, R.id.lineView, "field 'lineView'");
    target.rateTextView = Utils.findRequiredViewAsType(source, R.id.rateTextView, "field 'rateTextView'", TextView.class);
    target.quantityCountTextView = Utils.findRequiredViewAsType(source, R.id.quantityCountTextView, "field 'quantityCountTextView'", TextView.class);
    target.descriptionTextView = Utils.findRequiredViewAsType(source, R.id.descriptionTextView, "field 'descriptionTextView'", TextView.class);
    target.cGstAmountTextView = Utils.findRequiredViewAsType(source, R.id.cGstAmountTextView, "field 'cGstAmountTextView'", TextView.class);
    target.totalAmountTextView = Utils.findRequiredViewAsType(source, R.id.totalAmountTextView, "field 'totalAmountTextView'", TextView.class);
    target.sGstText = Utils.findRequiredViewAsType(source, R.id.sGstText, "field 'sGstText'", TextView.class);
    target.spareAndLabourListView = Utils.findRequiredView(source, R.id.spareAndLabourListView, "field 'spareAndLabourListView'");
  }

  @Override
  @CallSuper
  public void unbind() {
    VehicleReadyAdapter.VehicleReadyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lineView = null;
    target.rateTextView = null;
    target.quantityCountTextView = null;
    target.descriptionTextView = null;
    target.cGstAmountTextView = null;
    target.totalAmountTextView = null;
    target.sGstText = null;
    target.spareAndLabourListView = null;
  }
}
