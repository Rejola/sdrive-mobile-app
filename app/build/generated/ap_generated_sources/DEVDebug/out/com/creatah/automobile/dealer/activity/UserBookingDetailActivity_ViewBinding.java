// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserBookingDetailActivity_ViewBinding implements Unbinder {
  private UserBookingDetailActivity target;

  private View view2131296320;

  @UiThread
  public UserBookingDetailActivity_ViewBinding(UserBookingDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserBookingDetailActivity_ViewBinding(final UserBookingDetailActivity target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ackButton, "field 'ackButton' and method 'AckButtonClicked'");
    target.ackButton = Utils.castView(view, R.id.ackButton, "field 'ackButton'", Button.class);
    view2131296320 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.AckButtonClicked(p0);
      }
    });
    target.pickUpTimeTextView = Utils.findRequiredViewAsType(source, R.id.pickUpTimeTextView, "field 'pickUpTimeTextView'", TextView.class);
    target.pickUpDateTextView = Utils.findRequiredViewAsType(source, R.id.pickUpDateTextView, "field 'pickUpDateTextView'", TextView.class);
    target.pickUpTimeLayout = Utils.findRequiredViewAsType(source, R.id.pickUpTimeLayout, "field 'pickUpTimeLayout'", LinearLayout.class);
    target.pickUpDateLayout = Utils.findRequiredViewAsType(source, R.id.pickUpDateLayout, "field 'pickUpDateLayout'", LinearLayout.class);
    target.mapLayout = Utils.findRequiredViewAsType(source, R.id.mapLayout, "field 'mapLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserBookingDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ackButton = null;
    target.pickUpTimeTextView = null;
    target.pickUpDateTextView = null;
    target.pickUpTimeLayout = null;
    target.pickUpDateLayout = null;
    target.mapLayout = null;

    view2131296320.setOnClickListener(null);
    view2131296320 = null;
  }
}
