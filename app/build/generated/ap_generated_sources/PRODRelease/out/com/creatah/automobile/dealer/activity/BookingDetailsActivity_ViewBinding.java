// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BookingDetailsActivity_ViewBinding implements Unbinder {
  private BookingDetailsActivity target;

  @UiThread
  public BookingDetailsActivity_ViewBinding(BookingDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BookingDetailsActivity_ViewBinding(BookingDetailsActivity target, View source) {
    this.target = target;

    target.checkInDateTextView = Utils.findRequiredViewAsType(source, R.id.checkInDateTextView, "field 'checkInDateTextView'", TextView.class);
    target.serviceProgressDate = Utils.findRequiredViewAsType(source, R.id.serviceProgressDate, "field 'serviceProgressDate'", TextView.class);
    target.vehicleReadyDate = Utils.findRequiredViewAsType(source, R.id.vehicleReadyDate, "field 'vehicleReadyDate'", TextView.class);
    target.vehicleDeliveredDate = Utils.findRequiredViewAsType(source, R.id.vehicleDeliveredDate, "field 'vehicleDeliveredDate'", TextView.class);
    target.vehicleDeliveredDateLayout = Utils.findRequiredViewAsType(source, R.id.vehicleDeliveredDateLayout, "field 'vehicleDeliveredDateLayout'", LinearLayout.class);
    target.vehicleReadyDateLayout = Utils.findRequiredViewAsType(source, R.id.vehicleReadyDateLayout, "field 'vehicleReadyDateLayout'", LinearLayout.class);
    target.serviceProgressDateLayout = Utils.findRequiredViewAsType(source, R.id.serviceProgressDateLayout, "field 'serviceProgressDateLayout'", LinearLayout.class);
    target.checkinDateLayout = Utils.findRequiredViewAsType(source, R.id.checkinDateLayout, "field 'checkinDateLayout'", LinearLayout.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
    target.descriptionTextView = Utils.findRequiredViewAsType(source, R.id.descriptionTextView, "field 'descriptionTextView'", TextView.class);
    target.progressLayout = Utils.findRequiredViewAsType(source, R.id.progressLayout, "field 'progressLayout'", LinearLayout.class);
    target.jobCardDetailsLayout = Utils.findRequiredViewAsType(source, R.id.jobCardDetailsLayout, "field 'jobCardDetailsLayout'", LinearLayout.class);
    target.noJObCardFound = Utils.findRequiredViewAsType(source, R.id.noJObCardFound, "field 'noJObCardFound'", LinearLayout.class);
    target.noJobCardText = Utils.findRequiredViewAsType(source, R.id.noJobCardText, "field 'noJobCardText'", TextView.class);
    target.approximate_date_timeLinearLayout = Utils.findRequiredViewAsType(source, R.id.approximate_date_timeLinearLayout, "field 'approximate_date_timeLinearLayout'", CardView.class);
    target.approximateDeliveryDate = Utils.findRequiredViewAsType(source, R.id.approximateDeliveryDate, "field 'approximateDeliveryDate'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BookingDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.checkInDateTextView = null;
    target.serviceProgressDate = null;
    target.vehicleReadyDate = null;
    target.vehicleDeliveredDate = null;
    target.vehicleDeliveredDateLayout = null;
    target.vehicleReadyDateLayout = null;
    target.serviceProgressDateLayout = null;
    target.checkinDateLayout = null;
    target.retryButton = null;
    target.descriptionTextView = null;
    target.progressLayout = null;
    target.jobCardDetailsLayout = null;
    target.noJObCardFound = null;
    target.noJobCardText = null;
    target.approximate_date_timeLinearLayout = null;
    target.approximateDeliveryDate = null;
  }
}
