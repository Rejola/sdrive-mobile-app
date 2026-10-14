// Generated code from Butter Knife. Do not modify!
package com.sdrive.workshop.app.dev;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DispatchDelivery_ViewBinding implements Unbinder {
  private DispatchDelivery target;

  @UiThread
  public DispatchDelivery_ViewBinding(DispatchDelivery target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DispatchDelivery_ViewBinding(DispatchDelivery target, View source) {
    this.target = target;

    target.searchEditText = Utils.findRequiredViewAsType(source, R.id.searchText, "field 'searchEditText'", EditText.class);
    target.searchIcon = Utils.findRequiredViewAsType(source, R.id.searchIcon, "field 'searchIcon'", ImageView.class);
    target.footerLayout = Utils.findRequiredViewAsType(source, R.id.footerLayout, "field 'footerLayout'", LinearLayout.class);
    target.searchLayout = Utils.findRequiredViewAsType(source, R.id.searchLayout, "field 'searchLayout'", LinearLayout.class);
    target.customerLayout = Utils.findRequiredViewAsType(source, R.id.customerLayout, "field 'customerLayout'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.noCustomerFoundLayout = Utils.findRequiredViewAsType(source, R.id.noCustomerFoundLayout, "field 'noCustomerFoundLayout'", LinearLayout.class);
    target.progressLayout = Utils.findRequiredViewAsType(source, R.id.progressLayout, "field 'progressLayout'", LinearLayout.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
    target.noCustomerFound = Utils.findRequiredViewAsType(source, R.id.noCustomerFound, "field 'noCustomerFound'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DispatchDelivery target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchEditText = null;
    target.searchIcon = null;
    target.footerLayout = null;
    target.searchLayout = null;
    target.customerLayout = null;
    target.swipeRefreshLayout = null;
    target.noCustomerFoundLayout = null;
    target.progressLayout = null;
    target.retryButton = null;
    target.noCustomerFound = null;
  }
}
