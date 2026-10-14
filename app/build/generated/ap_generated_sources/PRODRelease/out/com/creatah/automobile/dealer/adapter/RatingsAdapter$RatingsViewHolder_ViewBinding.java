// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RatingsAdapter$RatingsViewHolder_ViewBinding implements Unbinder {
  private RatingsAdapter.RatingsViewHolder target;

  @UiThread
  public RatingsAdapter$RatingsViewHolder_ViewBinding(RatingsAdapter.RatingsViewHolder target,
      View source) {
    this.target = target;

    target.nameTextView = Utils.findRequiredViewAsType(source, R.id.nameTextView, "field 'nameTextView'", TextView.class);
    target.addressTextView = Utils.findRequiredViewAsType(source, R.id.mobileNumberTextView, "field 'addressTextView'", TextView.class);
    target.serviceNameTextView = Utils.findRequiredViewAsType(source, R.id.serviceNameTextView, "field 'serviceNameTextView'", TextView.class);
    target.dateTextView = Utils.findRequiredViewAsType(source, R.id.dateTextView, "field 'dateTextView'", TextView.class);
    target.feedbackTextView = Utils.findRequiredViewAsType(source, R.id.feedbackTextView, "field 'feedbackTextView'", TextView.class);
    target.ratingsLayout = Utils.findRequiredViewAsType(source, R.id.ratingsLayout, "field 'ratingsLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RatingsAdapter.RatingsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.nameTextView = null;
    target.addressTextView = null;
    target.serviceNameTextView = null;
    target.dateTextView = null;
    target.feedbackTextView = null;
    target.ratingsLayout = null;
  }
}
