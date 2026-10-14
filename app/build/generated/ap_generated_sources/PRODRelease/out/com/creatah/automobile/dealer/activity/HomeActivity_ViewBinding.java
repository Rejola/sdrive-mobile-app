// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target, View source) {
    this.target = target;

    target.reminderTodayLayout = Utils.findRequiredViewAsType(source, R.id.reminderTodayLayout, "field 'reminderTodayLayout'", LinearLayout.class);
    target.leadsLayout = Utils.findRequiredViewAsType(source, R.id.leadsLayout, "field 'leadsLayout'", LinearLayout.class);
    target.avgRatingLayout = Utils.findRequiredViewAsType(source, R.id.avgRatingLayout, "field 'avgRatingLayout'", LinearLayout.class);
    target.completedLayout = Utils.findRequiredViewAsType(source, R.id.completedLayout, "field 'completedLayout'", LinearLayout.class);
    target.reminderCountTextView = Utils.findRequiredViewAsType(source, R.id.reminderCountTextView, "field 'reminderCountTextView'", TextView.class);
    target.leadsCountTextView = Utils.findRequiredViewAsType(source, R.id.leadsCountTextView, "field 'leadsCountTextView'", TextView.class);
    target.ratingTextView = Utils.findRequiredViewAsType(source, R.id.ratingTextView, "field 'ratingTextView'", TextView.class);
    target.completedCountTextView = Utils.findRequiredViewAsType(source, R.id.completedCountTextView, "field 'completedCountTextView'", TextView.class);
    target.logoutRelativeLayout = Utils.findRequiredViewAsType(source, R.id.logoutRelativeLayout, "field 'logoutRelativeLayout'", RelativeLayout.class);
    target.container = Utils.findRequiredViewAsType(source, R.id.container, "field 'container'", LinearLayout.class);
    target.progressLayout = Utils.findRequiredViewAsType(source, R.id.progressLayout, "field 'progressLayout'", LinearLayout.class);
    target.noInternetConnection = Utils.findRequiredViewAsType(source, R.id.noInternetConnection, "field 'noInternetConnection'", TextView.class);
    target.homeLayout = Utils.findRequiredViewAsType(source, R.id.homeLayout, "field 'homeLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.reminderTodayLayout = null;
    target.leadsLayout = null;
    target.avgRatingLayout = null;
    target.completedLayout = null;
    target.reminderCountTextView = null;
    target.leadsCountTextView = null;
    target.ratingTextView = null;
    target.completedCountTextView = null;
    target.logoutRelativeLayout = null;
    target.container = null;
    target.progressLayout = null;
    target.noInternetConnection = null;
    target.homeLayout = null;
  }
}
