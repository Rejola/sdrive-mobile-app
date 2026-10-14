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

public class UserEnquiryFragment_ViewBinding implements Unbinder {
  private UserEnquiryFragment target;

  @UiThread
  public UserEnquiryFragment_ViewBinding(UserEnquiryFragment target, View source) {
    this.target = target;

    target.progressLayout = Utils.findRequiredViewAsType(source, R.id.progressLayout, "field 'progressLayout'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.footerLayout = Utils.findRequiredViewAsType(source, R.id.footerLayout, "field 'footerLayout'", LinearLayout.class);
    target.noEnquiries = Utils.findRequiredViewAsType(source, R.id.noEnquiries, "field 'noEnquiries'", TextView.class);
    target.noEnquiryFound = Utils.findRequiredViewAsType(source, R.id.noEnquiryFound, "field 'noEnquiryFound'", LinearLayout.class);
    target.retryButton = Utils.findRequiredViewAsType(source, R.id.retryButton, "field 'retryButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UserEnquiryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressLayout = null;
    target.swipeRefreshLayout = null;
    target.footerLayout = null;
    target.noEnquiries = null;
    target.noEnquiryFound = null;
    target.retryButton = null;
  }
}
