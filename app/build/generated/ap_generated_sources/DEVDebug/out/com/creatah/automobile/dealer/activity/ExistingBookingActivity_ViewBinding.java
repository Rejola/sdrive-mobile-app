// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ExistingBookingActivity_ViewBinding implements Unbinder {
  private ExistingBookingActivity target;

  @UiThread
  public ExistingBookingActivity_ViewBinding(ExistingBookingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ExistingBookingActivity_ViewBinding(ExistingBookingActivity target, View source) {
    this.target = target;

    target.mobileNoTextView = Utils.findRequiredViewAsType(source, R.id.mobileNoTextView, "field 'mobileNoTextView'", TextView.class);
    target.nameTextView = Utils.findRequiredViewAsType(source, R.id.nameTextView, "field 'nameTextView'", TextView.class);
    target.emailTextView = Utils.findRequiredViewAsType(source, R.id.emailTextView, "field 'emailTextView'", TextView.class);
    target.addressTextView = Utils.findRequiredViewAsType(source, R.id.mobileNumberTextView, "field 'addressTextView'", TextView.class);
    target.vehicleNoTextView = Utils.findRequiredViewAsType(source, R.id.addressTextView, "field 'vehicleNoTextView'", TextView.class);
    target.MakeTextView = Utils.findRequiredViewAsType(source, R.id.MakeTextView, "field 'MakeTextView'", TextView.class);
    target.ModelTextView = Utils.findRequiredViewAsType(source, R.id.ModelTextView, "field 'ModelTextView'", TextView.class);
    target.serviceSpinner = Utils.findRequiredViewAsType(source, R.id.serviceSpinner, "field 'serviceSpinner'", Spinner.class);
    target.serviceDate = Utils.findRequiredViewAsType(source, R.id.serviceDate, "field 'serviceDate'", EditText.class);
    target.descriptionEditText = Utils.findRequiredViewAsType(source, R.id.descriptionEditText, "field 'descriptionEditText'", EditText.class);
    target.addMoreButton = Utils.findRequiredViewAsType(source, R.id.addMoreButton, "field 'addMoreButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ExistingBookingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mobileNoTextView = null;
    target.nameTextView = null;
    target.emailTextView = null;
    target.addressTextView = null;
    target.vehicleNoTextView = null;
    target.MakeTextView = null;
    target.ModelTextView = null;
    target.serviceSpinner = null;
    target.serviceDate = null;
    target.descriptionEditText = null;
    target.addMoreButton = null;
  }
}
