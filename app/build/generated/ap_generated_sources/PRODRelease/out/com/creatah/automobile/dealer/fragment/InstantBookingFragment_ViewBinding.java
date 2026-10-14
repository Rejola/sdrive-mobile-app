// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InstantBookingFragment_ViewBinding implements Unbinder {
  private InstantBookingFragment target;

  @UiThread
  public InstantBookingFragment_ViewBinding(InstantBookingFragment target, View source) {
    this.target = target;

    target.progressLayout = Utils.findRequiredViewAsType(source, R.id.progressLayout, "field 'progressLayout'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.noInstanceServiceFound = Utils.findRequiredViewAsType(source, R.id.noInstanceServiceFound, "field 'noInstanceServiceFound'", LinearLayout.class);
    target.noInstanceService = Utils.findRequiredViewAsType(source, R.id.noInstanceService, "field 'noInstanceService'", TextView.class);
    target.footerLayout = Utils.findRequiredViewAsType(source, R.id.footerLayout, "field 'footerLayout'", LinearLayout.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    InstantBookingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressLayout = null;
    target.swipeRefreshLayout = null;
    target.noInstanceServiceFound = null;
    target.noInstanceService = null;
    target.footerLayout = null;
    target.retryButton = null;
  }
}
